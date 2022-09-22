package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.entities.author.AuthorType;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.entities.author.BookAuthorType;
import org.springframework.data.repository.CrudRepository;

public interface BookAuthorTypeRepository extends CrudRepository<BookAuthorType, Long> {
boolean existsByBookAndAuthorAndAuthorType (Book book, Author author, AuthorType authorType);
}