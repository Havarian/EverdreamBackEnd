package everdream.user.dataBase.repositories;

import everdream.user.dataBase.entities.ERole;
import everdream.user.dataBase.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository <Role, Long> {
    boolean existsByName (ERole role);
    Optional<Role> findByName(ERole role);
}
