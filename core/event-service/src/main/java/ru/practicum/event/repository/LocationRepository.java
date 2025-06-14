package ru.practicum.event.repository;

import ru.practicum.event.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByLatAndLon(Float lat, Float lon);
}
