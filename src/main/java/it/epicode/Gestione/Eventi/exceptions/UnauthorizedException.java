package it.epicode.Gestione.Eventi.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You don't have the permissions to access to this resource.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
