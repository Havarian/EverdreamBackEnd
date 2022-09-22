package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.author.AuthorType;
import everdream.content.dataBase.entities.author.EAuthorType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorTypeRepository extends CrudRepository<AuthorType, Long> {
    boolean existsByType (EAuthorType type);
    Optional<AuthorType> findByType (EAuthorType type);
}