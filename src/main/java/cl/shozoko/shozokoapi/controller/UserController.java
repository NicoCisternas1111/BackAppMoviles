package cl.shozoko.shozokoapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.shozoko.shozokoapi.dto.UserDTOs.ChangePasswordRequest;
import cl.shozoko.shozokoapi.dto.UserDTOs.UpdateProfileRequest;
import cl.shozoko.shozokoapi.dto.UserDTOs.UserRequest;
import cl.shozoko.shozokoapi.dto.UserDTOs.UserResponse;
import cl.shozoko.shozokoapi.model.User;
import cl.shozoko.shozokoapi.repository.UserRepository;
import cl.shozoko.shozokoapi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // ===== ADMIN: listar todos =====
    @GetMapping
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
    }

    // ===== ADMIN: obtener por id =====
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ===== ADMIN: crear usuario =====
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest req) {
        User u = userService.createUser(req.name, req.email, req.password, req.role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()));
    }

    // ===== ADMIN: actualizar usuario =====
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserRequest req) {
        User u = userService.updateUser(id, req.name, req.email, req.role);
        return ResponseEntity.ok(new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()));
    }

    // ===== ADMIN: eliminar usuario =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===== USER: actualizar su perfil =====
    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponse> updateProfile(@PathVariable Long id,
                                                      @RequestBody UpdateProfileRequest req) {
        User u = userService.updateProfile(id, req);
        return ResponseEntity.ok(new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()));
    }

    // ===== USER: cambiar contraseña =====
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody ChangePasswordRequest req) {
        userService.changePassword(id, req);
        return ResponseEntity.noContent().build();
    }
}
