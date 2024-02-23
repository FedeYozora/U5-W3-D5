package it.epicode.Gestione.Eventi.payloads;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewUserDTO(
        @NotNull(message = "You must enter a Name")
        @Size(min = 2, message = "The Name should be of at least 2 characters")
        String name,
        @NotNull(message = "You must enter a Surname")
        @Size(min = 2, message = "The Surname should be of at least 2 characters")
        String surname,
        @NotNull(message = "You must enter a Username")
        @Size(min = 2, message = "The Username should be of at least 2 characters")
        String username,
        @NotBlank(message = "You must enter an Email")
        @Email(message = "Insert a valid E-mail")
        String email,
        String password,
        String avatar,
        Role role,
        List<Event> events
) {
}
