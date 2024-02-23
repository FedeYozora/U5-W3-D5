package it.epicode.Gestione.Eventi.service;

import it.epicode.Gestione.Eventi.entities.User;
import it.epicode.Gestione.Eventi.enums.Role;
import it.epicode.Gestione.Eventi.payloads.NewUserDTO;
import it.epicode.Gestione.Eventi.payloads.UserLoginDTO;
import it.epicode.Gestione.Eventi.repos.UserRepo;
import it.epicode.Gestione.Eventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new RuntimeException("Check your credentials");
        }
    }

    public User save(@RequestBody NewUserDTO payload) {
        User newUser = new User();
        newUser.setUsername(payload.username());
        newUser.setName(payload.name());
        newUser.setSurname(payload.surname());
        newUser.setEmail(payload.email());
        newUser.setPassword(bcrypt.encode(payload.password()));
        newUser.setRole(Role.UTENTE);
        return userRepo.save(newUser);
    }
}
