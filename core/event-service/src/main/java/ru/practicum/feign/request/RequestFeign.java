package ru.practicum.feign.request;

import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.request.RequestStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "request-service")
public interface RequestFeign {
    @GetMapping("requests/events/{eventId}/{status}")
    List<ParticipationRequestDto> findAllByEventIdInAndStatus(@PathVariable(name = "eventId") List<Long> eventsId,
                                                              @PathVariable RequestStatus status);

    @GetMapping("requests/events/{eventId}/{status}/count")
    Long findCountByEventIdInAndStatus(@PathVariable Long eventId, @PathVariable RequestStatus status);

    @GetMapping("/users/{userId}/events/{eventId}/requests/requester")
    Optional<ParticipationRequestDto> findByRequesterIdAndEventId(@PathVariable Long userId, @PathVariable Long eventId);
}