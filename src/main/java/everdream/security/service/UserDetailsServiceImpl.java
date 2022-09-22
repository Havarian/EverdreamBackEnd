package everdream.security.service;

import everdream.user.controllers.dto.UserDto;
import everdream.user.exceptions.EmailNotFoundException;
import everdream.user.services.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserService appUserService;

    public UserDetailsServiceImpl(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {

        UserDto user = appUserService.getUserByEmail(email);
        return UserDetailsImpl.build(user);
    }
}
