package it.epicode.Gestione.Eventi.repos;

import it.epicode.Gestione.Eventi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Integer> {
}
