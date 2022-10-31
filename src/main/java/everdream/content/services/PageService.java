package everdream.content.services;

import everdream.content.controllers.dtos.PageDto;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PageService {
    Page savePage (Book book, PageDto pageDto) throws IOException, InterruptedException;
    void deletePagesByBookId (Long bookId);
    void deletePage (Long pageId);
}