package it.epicode.Gestione.Eventi.payloads;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.enums.Role;

import java.util.List;

public record NewUserDTO(
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
