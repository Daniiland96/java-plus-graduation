package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.EventSimilarity;

import java.util.Optional;

@Repository
public interface EventSimilarityRepository extends JpaRepository<EventSimilarity, Long> {
    Boolean existsByEventAAndEventB(Long eventA, Long eventB);

    Optional<EventSimilarity> findByEventAAndEventB(Long eventA, Long eventB);
}