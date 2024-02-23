package it.epicode.Gestione.Eventi.payloads;

import it.epicode.Gestione.Eventi.entities.User;

import java.time.LocalDate;
import java.util.List;

public record NewEventDTO(
        String title,
        String description,
        LocalDate date,
        String location,
        Integer availableSeats,
        List<User> users
) {
}
