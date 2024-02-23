package it.epicode.Gestione.Eventi.repos;

import it.epicode.Gestione.Eventi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
