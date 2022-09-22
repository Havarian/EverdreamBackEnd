package everdream.content.services;

import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.page.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PageService {

    void addPageToBook (Book book, Page page, Long parentPageId);
    Page addVideoToPage (MultipartFile file, Long pageId);
    Page addAudioToPage (MultipartFile file, Long pageId);
    Page addTextToPage (MultipartFile file, Long pageId);

}