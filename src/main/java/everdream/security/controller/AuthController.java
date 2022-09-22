package everdream.security.controller;

import everdream.security.controller.payload.JwtResponse;
import everdream.security.controller.payload.LoginRequest;
import everdream.security.controller.payload.RegisterRequest;
import everdream.security.service.AuthService;
import everdream.user.exceptions.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/security")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register (@RequestBody RegisterRequest request) {

        try {
            authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("UserRegistered!");
        } catch (UserExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong!");
        }
    }

    @PostMapping ("/login")
    @Transactional
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {

        try {
            JwtResponse response = authService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to login: " + e.getMessage());
        }
    }
}
