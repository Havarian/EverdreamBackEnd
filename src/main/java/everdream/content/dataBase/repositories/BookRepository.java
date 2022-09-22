package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.book.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom{
    Iterable<Book> findAllByInCreationIsTrueAndCreatorId (Long id);
    @Query("select b from Book b where b.isPublished = false and b.inCreation = false")
    Iterable<Book> findAllByPublishedIsFalseAndInCreationIsFalse ();
    @Query("select b from Book b where b.isPublished = true")
    Iterable<Book> findAllByPublishedIsTrue();
}