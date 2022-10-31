package everdream.content.services;

import everdream.content.controllers.dtos.PageDto;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.page.Page;
import everdream.content.dataBase.entities.page.PageTree;
import everdream.content.dataBase.repositories.PageRepository;
import everdream.content.dataBase.repositories.PageTreeRepository;
import everdream.content.services.contentMapper.ContentMapper;
import everdream.fileManagement.awsS3.service.AWSService;
import everdream.user.services.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageTreeRepository pageTreeRepository;
    private final AWSService awsService;

    public PageServiceImpl(PageRepository pageRepository, PageTreeRepository pageTreeRepository, AWSService awsService) {
        this.pageRepository = pageRepository;
        this.pageTreeRepository = pageTreeRepository;
        this.awsService = awsService;
    }

    @Override
    public Page savePage(Book book, PageDto pageDto) throws IllegalArgumentException, IOException, InterruptedException {

        pageDto.setCreatorId(AppUserService.getCurrentUserId());
        Page page = pageRepository.save(ContentMapper.fromPageDto(pageDto));

        if (pageDto.getId() == null) {
            if (pageDto.getParentId() == null){
                pageTreeRepository.save(new PageTree(book, page));
            } else {
                Page parentPage = pageRepository.findById(pageDto.getParentId())
                        .orElseThrow(() -> new IllegalArgumentException("Page with id: " + pageDto.getParentId() + " does not exists"));
                pageTreeRepository.save(new PageTree(book, page, parentPage));
            }
        }

        awsService.saveFileToS3(pageDto.getVideo().getFileName());

        return page;
    }

    @Override
    public void deletePagesByBookId(Long bookId) {
        pageTreeRepository.deleteByBookId(bookId);
    }

    @Override
    @Transactional
    public void deletePage(Long pageId) throws IllegalArgumentException{
        if (pageId != null) {
            PageTree currentPageTree = pageTreeRepository.findByCurrentPageId(pageId)
                    .orElseThrow(() -> new IllegalArgumentException("Page with id: " + pageId + " does not exists"));
            awsService.deleteFileFromS3(currentPageTree.getCurrentPage().getVideo().getFileName());
            pageTreeRepository.deleteByCurrentPageId(pageId);
            pageTreeRepository.findByParentPageId(pageId).forEach(pageTree -> deletePage(pageTree.getCurrentPage().getId()));
        }
    }
}
