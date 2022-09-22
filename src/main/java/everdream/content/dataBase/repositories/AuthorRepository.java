package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.author.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}