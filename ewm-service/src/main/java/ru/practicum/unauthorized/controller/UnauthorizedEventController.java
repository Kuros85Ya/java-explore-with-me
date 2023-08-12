package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;
import ru.practicum.unauthorized.service.UnauthorizedEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ru.practicum.common.Utils.toPageRequest;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedEventController {

    private final UnauthorizedEventService service;

    @GetMapping()
    public List<CommonSingleEventResponse> getEvents(
            @RequestParam(defaultValue = "") String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Long maxDistance,
            @RequestParam(required = false) SortType sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        log.info("Поиск событий неавторизованным польозователем по параметрам text = {} categories = {} paid = {} rangeStart = {} rangeEnd = {} onlyAvailbale = {} sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, latitude, longitude, maxDistance, sort, pageRequest, request.getRemoteAddr(), request.getRequestURI());
    }

    @GetMapping("/{eventId}")
    public CommonSingleEventResponse getEventById(@PathVariable Long eventId, HttpServletRequest request) {
        log.info("Поиск события любым пользователем по id = {}, ip = {}, path = {}", eventId, request.getRemoteAddr(), request.getRequestURI());
        return service.getEventById(eventId, request.getRemoteAddr(), request.getRequestURI());
    }
}
