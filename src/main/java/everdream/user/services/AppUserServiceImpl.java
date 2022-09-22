package everdream.user.services;

import everdream.user.controllers.dto.UserDto;
import everdream.user.controllers.dto.UserMapper;
import everdream.user.dataBase.entities.ERole;
import everdream.user.dataBase.entities.User;
import everdream.user.dataBase.entities.UserRole;
import everdream.user.dataBase.repositories.RoleRepository;
import everdream.user.dataBase.repositories.UserRepository;
import everdream.user.dataBase.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public AppUserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void saveUserDto(UserDto userDTO) throws IllegalArgumentException, EntityNotFoundException {
        User user = UserMapper.userDtoToUser(userDTO);
        User savedUser = userRepository.save(user);
        addRolesToUser(savedUser.getId(), userDTO.getRoles());
    }
    @Transactional
    @Override
    public void removeUser(Long id) throws NoSuchElementException{
        userRoleRepository.deleteAllByUser_Id(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addRolesToUser(Long id, List<String> roles) throws IllegalArgumentException,
            EntityNotFoundException{
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User not found!"));
        roles.removeIf(role -> userRoleRepository.existsUserRolesByUserAndRole_Name(user, ERole.valueOf(role)));
        roles.forEach(role ->
                userRoleRepository.save(new UserRole(user,
                        roleRepository.findByName(ERole.valueOf(role))
                                .orElseThrow(() -> new EntityNotFoundException("Role " + role + "not found")))));
    }

    @Override
    public void removeRoleFromUser(Long id, List<String> roles) throws IllegalArgumentException, EntityNotFoundException{
        roles.forEach(role -> userRoleRepository.removeUserRoleByUser_IdAndRole_Name(id, ERole.valueOf(role)));
    }

    @Override
    public boolean existsByEmail(String email) throws IllegalArgumentException, EntityNotFoundException{
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto getUserByEmail(String email) throws NoSuchElementException {
        return UserMapper.userToUserDto(userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email: " + email + " not found!")));
    }

    @Override
    public UserDto getUserByName(String username) {
        return UserMapper.userToUserDto(userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with name: " + username + " not found!")));
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + id + " not found!")));
    }

    @Override
    public Set<UserDto> getAllUsers() throws NoResultException {
        return userRepository.findAll().stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toSet());
    }
}