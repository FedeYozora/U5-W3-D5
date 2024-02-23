package it.epicode.Gestione.Eventi.controllers;

import it.epicode.Gestione.Eventi.entities.User;
import it.epicode.Gestione.Eventi.payloads.NewUserDTO;
import it.epicode.Gestione.Eventi.service.AuthService;
import it.epicode.Gestione.Eventi.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllUser(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findByID(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return authService.save(body);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.OK)
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated NewUserDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return userService.findByIdAndUpdate(id, body);
        }
    }

    @PostMapping("/upload/{id}")
    public String uploadFile(@RequestParam("avatar") MultipartFile body, @PathVariable UUID id) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return userService.uploadImg(body, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        userService.findByIdAndDelete(id);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getMyProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails putMyProfile(@AuthenticationPrincipal User currentUser, @RequestBody NewUserDTO body) {
        return userService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentUser) {
        userService.findByIdAndDelete(currentUser.getId());
    }
}
