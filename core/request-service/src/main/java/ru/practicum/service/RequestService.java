package ru.practicum.service;

import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequest);

    List<ParticipationRequestDto> findAllByEventIdInAndStatus(List<Long> eventsId, RequestStatus status);

    Long findCountByEventIdInAndStatus(Long eventId, RequestStatus status);

    Optional<ParticipationRequestDto> findByRequesterIdAndEventId(Long userId, Long eventId);
}