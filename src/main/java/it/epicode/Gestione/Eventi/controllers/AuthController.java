package it.epicode.Gestione.Eventi.controllers;

import it.epicode.Gestione.Eventi.entities.User;
import it.epicode.Gestione.Eventi.payloads.LoginResponseDTO;
import it.epicode.Gestione.Eventi.payloads.NewUserDTO;
import it.epicode.Gestione.Eventi.payloads.UserLoginDTO;
import it.epicode.Gestione.Eventi.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginDTO body) {
        return new LoginResponseDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    public User newUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return authService.save(body);
        }
    }
}
