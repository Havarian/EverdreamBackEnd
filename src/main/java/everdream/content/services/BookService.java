package everdream.content.services;

import everdream.content.controllers.dtos.BookDto;
import everdream.content.controllers.dtos.PageDto;
import everdream.content.dataBase.entities.book.Book;

import java.io.IOException;
import java.util.Set;

public interface BookService {
    Book saveBook (BookDto bookDto) throws IOException, InterruptedException;
    BookDto saveBookAndRefresh (BookDto bookDto) throws IOException, InterruptedException;
    void deleteBook (Long bookId);
    Set<BookDto> getBooksInCreation ();
    Set<BookDto> getPublishedBooks ();
    Set<BookDto> getPendingBooks();
    BookDto getBookDtoById (Long bookId);
}
