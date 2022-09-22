package everdream.content.services;

import everdream.content.controllers.dtos.AuthorDto;
import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.entities.author.BookAuthorType;
import everdream.content.dataBase.entities.book.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AuthorService {
    Author saveAuthor (Author author) throws IOException, InterruptedException;
    void deleteAuthor (Long authorId);
    void addAuthorToBook (Book book, AuthorDto authorDto) throws IOException, InterruptedException;
    void removeAuthorFromBook (BookAuthorType bookAuthorType);
}
