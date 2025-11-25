package cl.shozoko.shozokoapi.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.shozoko.shozokoapi.dto.UserDTOs.ChangePasswordRequest;
import cl.shozoko.shozokoapi.dto.UserDTOs.UpdateProfileRequest;
import cl.shozoko.shozokoapi.model.Role;
import cl.shozoko.shozokoapi.model.User;
import cl.shozoko.shozokoapi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User register(String name, String email, String rawPassword, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        String hash = passwordEncoder.encode(rawPassword);
        User user = new User(name, email, hash, role);
        return userRepository.save(user);
    }

    public boolean verifyPassword(String rawPassword, String hash) {
        return passwordEncoder.matches(rawPassword, hash);
    }

        public User createUser(String name, String email, String rawPassword, Role role) {
        return register(name, email, rawPassword, role); // misma lógica que register
    }

    public User updateUser(Long id, String name, String email, Role role) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        u.setName(name);
        u.setEmail(email);
        u.setRole(role);
        return userRepository.save(u);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    // ==== Perfil ====

    public User updateProfile(Long id, UpdateProfileRequest req) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        u.setName(req.name);
        u.setEmail(req.email);
        return userRepository.save(u);
    }

    public void changePassword(Long id, ChangePasswordRequest req) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!verifyPassword(req.currentPassword, u.getPasswordHash())) {
            throw new RuntimeException("La contraseña actual no es correcta");
        }

        String newHash = passwordEncoder.encode(req.newPassword);
        u.setPasswordHash(newHash);
        userRepository.save(u);
    }
}
