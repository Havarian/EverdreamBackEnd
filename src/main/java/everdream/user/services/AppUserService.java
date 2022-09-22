package everdream.user.services;

import everdream.security.service.UserDetailsImpl;
import everdream.user.controllers.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface AppUserService {

    static List<String> getCurrentUserRoles () {

        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    static Long getCurrentUserId (){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getId();
    }

    void saveUserDto (UserDto userDto);
    void addRolesToUser(Long id, List<String> roles);
    void removeRoleFromUser(Long id, List<String> roles);
    boolean existsByEmail(String email);
    UserDto getUserByEmail(String email);
    UserDto getUserByName(String username);
    UserDto getUserById (Long id);
    Set<UserDto> getAllUsers ();
    void removeUser (Long id);
}
