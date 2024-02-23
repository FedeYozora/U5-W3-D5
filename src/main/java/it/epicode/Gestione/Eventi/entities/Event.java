package it.epicode.Gestione.Eventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private Integer availableSeats;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<User> users;

    public Event(String title, String description, LocalDate date, String location, Integer availableSeats) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
    }
}
