package it.epicode.Gestione.Eventi.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.epicode.Gestione.Eventi.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record NewEventDTO(
        @NotNull(message = "You must enter a title")
        @Size(min = 5, message = "Insert a a title of at least 5 characters")
        String title,
        @NotNull(message = "You must enter a Description")
        @Size(min = 5, message = "Insert a description of at least 5 characters")
        String description,
        @NotNull(message = "You must enter a Date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotNull(message = "You must enter a Location")
        String location,
        @NotNull(message = "You must enter the number of empty seats")
        Integer availableSeats,
        List<User> users
) {
}
