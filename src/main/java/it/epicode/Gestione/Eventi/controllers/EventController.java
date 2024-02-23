package it.epicode.Gestione.Eventi.controllers;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.entities.User;
import it.epicode.Gestione.Eventi.payloads.NewEventDTO;
import it.epicode.Gestione.Eventi.service.EventService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Event> getEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getAllEvent(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event findById(@PathVariable Integer id) {
        return eventService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@RequestBody NewEventDTO newEventDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return eventService.save(newEventDTO);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.OK)
    public Event findByIdAndUpdate(@PathVariable Integer id, @RequestBody Event event) {
        return eventService.findByIdAndUpdate(id, event);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Integer id) {
        eventService.findByIdAndDelete(id);
    }

    @GetMapping("/myEvents")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getMyEvents(@AuthenticationPrincipal User currentUser) {
        return currentUser.getEvents();
    }
}
