package everdream.user.dataBase.repositories;

import everdream.user.dataBase.entities.ERole;
import everdream.user.dataBase.entities.User;
import everdream.user.dataBase.entities.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    void deleteAllByUser_Id (Long id);
    void removeUserRoleByUser_IdAndRole_Name (Long id, ERole role);
    boolean existsUserRolesByUserAndRole_Name (User user, ERole role);
}
