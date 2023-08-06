package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;
import ru.practicum.unauthorized.service.UnauthorizedEventService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.common.util.parseDttm;
import static ru.practicum.common.util.toPageRequest;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedEventController {

    private final UnauthorizedEventService service;

    @GetMapping()
    public List<CommonSingleEventResponse> getEvents(
            @RequestParam String text,
            @RequestParam List<Long> categories,
            @RequestParam Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam Boolean onlyAvailable,
            @RequestParam SortType sort,
            @RequestParam Integer from,
            @RequestParam Integer size) {
        log.info("Поиск событий неавторизованным польозователем по параметрам text = {} categories = {} paid = {} rangeStart = {} rangeEnd = {} onlyAvailbale = {} sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        PageRequest pageRequest = toPageRequest(from, size);

        LocalDateTime startDt;
        if (rangeStart == null && rangeEnd == null) {
            startDt = LocalDateTime.now();
        } else {
            startDt = parseDttm(rangeStart);
        }

        LocalDateTime endDt;
        if (rangeEnd == null) {
            endDt = LocalDateTime.MAX;
        } else {
            endDt = parseDttm(rangeEnd);
        }
        return service.getEvents(text, categories, paid, startDt, endDt, onlyAvailable, sort, pageRequest);
    }

    @GetMapping("/{eventId}")
    public CommonSingleEventResponse getEventById(@PathVariable Long eventId) {
        log.info("Поиск события любым пользователем по id = {}", eventId);
        return service.getEventById(eventId);
    }
}
