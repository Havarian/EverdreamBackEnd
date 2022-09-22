package everdream.security.service;

import everdream.security.controller.AuthController;
import everdream.security.controller.payload.JwtResponse;
import everdream.security.controller.payload.LoginRequest;
import everdream.security.controller.payload.RegisterRequest;
import everdream.security.token.TokenHandler;
import everdream.user.controllers.dto.UserDto;
import everdream.user.exceptions.UserExistsException;
import everdream.user.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AppUserService appUserService, AuthenticationManager authenticationManager,
                           TokenHandler tokenHandler, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) throws IllegalArgumentException{

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenHandler.generateJwtToken();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(
                jwtToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public void register(RegisterRequest registerRequest) throws RuntimeException{

        if (appUserService.existsByEmail(registerRequest.getEmail())){
            throw new UserExistsException("Email: " + registerRequest.getEmail() + " already in use!");
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        UserDto user = UserDto.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();

        appUserService.saveUserDto(user);
    }
}
