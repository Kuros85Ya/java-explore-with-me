package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;
import ru.practicum.common.mapper.RequestMapper;
import ru.practicum.unauthorized.service.UnauthorizedEventService;

import java.util.List;

import static ru.practicum.common.util.parseDttm;

@Controller
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedEventController {

    private final UnauthorizedEventService service;

    @GetMapping()
    public List<CommonSingleEventResponse> getEvents(
            @RequestParam String text,
            @RequestParam List<Integer> categories,
            @RequestParam Boolean paid,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam Boolean onlyAvailable,
            @RequestParam SortType sort,
            @RequestParam Integer from,
            @RequestParam Integer size) {
        log.info("Поиск событий неавторизованным польозователем по параметрам text = {} categories = {} paid = {} rangeStart = {} rangeEnd = {} onlyAvailbale = {} sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        PageRequest pageRequest = RequestMapper.toPageRequest(from, size);
        return service.getEvents(text, categories, paid, parseDttm(rangeStart), parseDttm(rangeEnd), onlyAvailable, sort, pageRequest);
    }

    @GetMapping("/{eventId}")
    public CommonSingleEventResponse getEventById(@PathVariable Long eventId) {
        log.info("Поиск события неавторизованным пользователем по id = {}", eventId);
        return service.getEventById(eventId);
    }
}
