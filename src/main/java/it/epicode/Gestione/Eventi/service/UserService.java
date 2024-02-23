package it.epicode.Gestione.Eventi.service;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.entities.User;
import it.epicode.Gestione.Eventi.exceptions.NoSeatsException;
import it.epicode.Gestione.Eventi.exceptions.NotFoundException;
import it.epicode.Gestione.Eventi.payloads.NewUserDTO;
import it.epicode.Gestione.Eventi.repos.EventRepo;
import it.epicode.Gestione.Eventi.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventService eventService;

    public Page<User> getAllUser(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepo.findAll(pageable);
    }

    public User findById(UUID uuid) {
        return userRepo.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public User findByIdAndUpdate(UUID uuid, NewUserDTO body) {
        User user = this.findById(uuid);
        user.setUsername(body.username());
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setAvatar(body.avatar());
        List<Event> eventoDisponibile = new ArrayList<>();
        for (Event event : body.events()) {
            Event eventId = eventService.findById(event.getId());
            if (eventId != null && eventId.getAvailableSeats() > 0) {
                eventoDisponibile.add(event);
                eventId.setAvailableSeats(eventId.getAvailableSeats() - eventId.getUsers().size());
            } else {
                throw new NoSeatsException("The event with ID " + event.getId() + " does not have any seats left.");
            }
        }
        user.setEvents(eventoDisponibile);
        return userRepo.save(user);
    }

    public void findByIdAndDelete(UUID uuid) {
        User user = this.findById(uuid);
        userRepo.delete(user);
    }
}
