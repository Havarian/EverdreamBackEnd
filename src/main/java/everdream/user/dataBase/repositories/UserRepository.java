package everdream.user.dataBase.repositories;

import everdream.user.dataBase.entities.User;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    boolean existsByEmail (String email);
    void deleteById (@NonNull Long id);
    Optional<User> findUserByEmail (String email);
    Optional<User> findUserByUsername (String username);
    Set<User> findAll ();
}
