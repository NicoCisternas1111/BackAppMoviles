package cl.shozoko.shozokoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.shozoko.shozokoapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
