package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.UserAction;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
}
