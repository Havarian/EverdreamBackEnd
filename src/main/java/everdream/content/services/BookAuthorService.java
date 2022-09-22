package everdream.content.services;

import everdream.content.controllers.dtos.AuthorDto;
import everdream.content.dataBase.entities.author.BookAuthorType;
import everdream.content.dataBase.entities.book.Book;

import java.io.IOException;

public interface BookAuthorService {

    void addAuthorToBook (Book book, AuthorDto authorDto) throws IOException, InterruptedException;
    void removeAuthorFromBook (BookAuthorType bookAuthorType);
}
