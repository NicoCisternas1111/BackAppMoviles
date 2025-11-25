package cl.shozoko.shozokoapi.dto;

import cl.shozoko.shozokoapi.model.Role;

public class UserDTOs {

    // Lo que se devuelve al front
    public static class UserResponse {
        public Long id;
        public String name;
        public String email;
        public Role role;

        public UserResponse(Long id, String name, String email, Role role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }

    // Crear / editar usuario (ADMIN)
    public static class UserRequest {
        public String name;
        public String email;
        public String password; // texto plano, se encripta en el servicio
        public Role role;
    }

    // Actualizar perfil (USER)
    public static class UpdateProfileRequest {
        public String name;
        public String email;
    }

    // Cambiar contraseña (USER)
    public static class ChangePasswordRequest {
        public String currentPassword;
        public String newPassword;
    }
}
