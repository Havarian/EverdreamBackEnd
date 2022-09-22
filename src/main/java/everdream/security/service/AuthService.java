package everdream.security.service;

import everdream.security.controller.payload.JwtResponse;
import everdream.security.controller.payload.LoginRequest;
import everdream.security.controller.payload.RegisterRequest;

public interface AuthService {
    JwtResponse login (LoginRequest loginRequest);
    void register (RegisterRequest registerRequest);
}
