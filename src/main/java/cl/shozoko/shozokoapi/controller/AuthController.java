package cl.shozoko.shozokoapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.shozoko.shozokoapi.dto.AuthDTOs.AuthResponse;
import cl.shozoko.shozokoapi.dto.AuthDTOs.LoginRequest;
import cl.shozoko.shozokoapi.dto.AuthDTOs.RegisterRequest;
import cl.shozoko.shozokoapi.model.User;
import cl.shozoko.shozokoapi.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ===== Registro =====
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        User u = userService.register(req.name, req.email, req.password, req.role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()));
    }

    // ===== Login =====
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return userService.findByEmail(req.email)
                .filter(u -> userService.verifyPassword(req.password, u.getPasswordHash()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(
                        new AuthResponse(u.getId(), u.getName(), u.getEmail(), u.getRole())))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas"));
    }
}
