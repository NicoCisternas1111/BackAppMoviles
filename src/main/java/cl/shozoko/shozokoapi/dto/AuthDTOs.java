package cl.shozoko.shozokoapi.dto;

import cl.shozoko.shozokoapi.model.Role;

public class AuthDTOs {

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
        public Role role; // ADMIN o USER
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public Long id;
        public String name;
        public String email;
        public Role role;

        public AuthResponse(Long id, String name, String email, Role role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }
}
