package ru.practicum.event.dto;

import ru.practicum.event.model.EventSort;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
public class ReqParam {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private EventSort sort;
    private int from;
    private int size;
}
