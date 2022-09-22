package everdream.content.services;

import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.page.Page;
import everdream.content.dataBase.repositories.PageRepository;
import everdream.user.services.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public void addPageToBook(Book book, Page page, Long parentPageId) throws IllegalArgumentException{
        page.setIsRootPage(book.getPages().size() == 1);
        page.setCreatorId(AppUserService.getCurrentUserId());
        pageRepository.save(page);
    }

    @Override
    public Page addVideoToPage(MultipartFile file, Long pageId) {
        return null;
    }

    @Override
    public Page addAudioToPage(MultipartFile file, Long pageId) {
        return null;
    }

    @Override
    public Page addTextToPage(MultipartFile file, Long pageId) {
        return null;
    }
}
