package everdream.user.controllers.dto;

import everdream.user.dataBase.entities.ERole;
import everdream.user.dataBase.entities.Role;
import everdream.user.dataBase.entities.User;
import everdream.user.dataBase.entities.UserRole;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperUnitTest {

    @Test
    void userToUserDto() {

        //Given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@test.pl");
        user.setPassword("testUser44");
        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(user, new Role(ERole.ROLE_USER)));
        roles.add(new UserRole(user, new Role(ERole.ROLE_CREATOR)));
        user.setUserRoles(roles);

        //When
        UserDto userDto = UserMapper.userToUserDto(user);

        //Then
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertTrue(userDto.getRoles().containsAll(user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName().toString()).collect(Collectors.toList())));
    }

    @Test
    void userDtoToUser() {

        //Given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_CREATOR");
        roles.add("ROLE_ADMIN");

        UserDto userDto = UserDto.builder()
                .id(4L)
                .username("testUsername")
                .email("test@email.pl")
                .password("testPassword")
                .roles(roles)
                .build();

        //When
        User user = UserMapper.userDtoToUser(userDto);

        //Then
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertTrue(userDto.getRoles().containsAll(user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName().toString()).collect(Collectors.toList())));

    }
}