package it.epicode.Gestione.Eventi.service;

import it.epicode.Gestione.Eventi.entities.Event;
import it.epicode.Gestione.Eventi.exceptions.NotFoundException;
import it.epicode.Gestione.Eventi.payloads.NewEventDTO;
import it.epicode.Gestione.Eventi.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Page<Event> getAllEvent(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepo.findAll(pageable);
    }

    public Event findById(Integer id) {
        return eventRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event save(@RequestBody NewEventDTO body) throws IOException {
        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setDate(body.date());
        newEvent.setLocation(body.location());
        newEvent.setAvailableSeats(body.availableSeats());
        return eventRepo.save(newEvent);
    }

    public Event findByIdAndUpdate(Integer id, Event body) {
        Event newEvent = eventRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        newEvent.setTitle(body.getTitle());
        newEvent.setDescription(body.getDescription());
        newEvent.setDate(body.getDate());
        newEvent.setLocation(body.getLocation());
        newEvent.setAvailableSeats(body.getAvailableSeats() - body.getUsers().size());
        Event event = eventRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        newEvent.setUsers(event.getUsers());
        return eventRepo.save(newEvent);
    }

    public void findByIdAndDelete(Integer id) {
        Event newEvent = this.findById(id);
        eventRepo.delete(newEvent);
    }
}
