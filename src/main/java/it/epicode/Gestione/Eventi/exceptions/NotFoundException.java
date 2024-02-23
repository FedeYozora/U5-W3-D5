package it.epicode.Gestione.Eventi.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Integer id) {
        super("The event with id: " + id + " has not been found!");
    }

    public NotFoundException(UUID uuid) {
        super("The user with uuid: " + uuid + " has not been found!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
