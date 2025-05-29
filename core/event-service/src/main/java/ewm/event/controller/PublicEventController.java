package ewm.event.controller;

import ewm.dto.event.EventFullDto;
import ewm.dto.event.EventShortDto;
import ewm.event.dto.ReqParam;
import ewm.event.model.EventSort;
import ewm.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static ewm.utility.Constants.FORMAT_DATETIME;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {

    @Value("${main.service.app.name}")
    private String mainServiceAppName;
    private final EventService eventService;
//    private final RestStatClient statClient;

    @GetMapping
    public List<EventShortDto> publicGetAllEvents(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT_DATETIME) LocalDateTime rangeStart,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT_DATETIME) LocalDateTime rangeEnd,
                                                  @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                  @RequestParam(required = false) EventSort sort,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        ReqParam reqParam = ReqParam.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();
        log.info("Публичный запрос на поиск событий по параметрам: {}", reqParam);
        List<EventShortDto> events = eventService.getAllEvents(reqParam);
//        hit(request);
        return events;
    }

    @GetMapping("/{id}")
    public EventFullDto publicGetEvent(@PathVariable long id,
                                       HttpServletRequest request) {
        EventFullDto eventFullDto = eventService.publicGetEvent(id);
//        hit(request);
        return eventFullDto;
    }

//    private void hit(HttpServletRequest request) {
//        ParamHitDto endpointHitDto = new ParamHitDto();
//        endpointHitDto.setApp(mainServiceAppName);
//        endpointHitDto.setUri(request.getRequestURI());
//        endpointHitDto.setIp(request.getRemoteAddr());
//        endpointHitDto.setTimestamp(LocalDateTime.now());
//        statClient.hit(endpointHitDto);
//    }
}