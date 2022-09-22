package everdream.user.controllers.dto;

import everdream.user.dataBase.entities.ERole;
import everdream.user.dataBase.entities.Role;
import everdream.user.dataBase.entities.User;
import everdream.user.dataBase.entities.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserMapper {

    static UserDto userToUserDto(User user) {
        List<String> list = new ArrayList<>();
        for (UserRole userRole : user.getUserRoles()) {
            String role = userRole.getRole().getName().name();
            list.add(role);
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                list);
    }

    static User userDtoToUser(UserDto userDTO) {

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        Set<UserRole> roles= userDTO.getRoles().stream()
                .map(role -> new UserRole(user, new Role(ERole.valueOf(role))))
                .collect(Collectors.toSet());

        user.setUserRoles(roles);

        return user;
    }
}