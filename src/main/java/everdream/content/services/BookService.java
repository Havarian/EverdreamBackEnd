package everdream.content.services;

import everdream.content.controllers.dtos.BookDto;
import everdream.content.dataBase.entities.book.Book;

import java.util.Set;

public interface BookService {
    Book saveBook (BookDto bookDto);
    BookDto saveBookAndRefresh (BookDto bookDto);
    void deleteBook (Long bookId);
    Set<BookDto> getBooksInCreation ();
    Set<BookDto> getPublishedBooks ();
    Set<BookDto> getPendingBooks();
}
