package it.epicode.Gestione.Eventi.payloads;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewUserDTO(
        @NotNull(message = "Name obbligatorio")
        @Size(min = 4, message = "The Name should be of at least 2 characters")
        String name,
        String surname,
        String username,
        String email,
        String password,
        String avatar,
        Role role,
        List<Event> events
) {
}
