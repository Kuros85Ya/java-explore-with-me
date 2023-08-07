package ru.practicum.authorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.service.AuthorizedEventService;
import ru.practicum.common.dto.CommonSingleEventResponse;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.common.util.toPageRequest;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthorizedEventController {

    private final AuthorizedEventService service;

    @GetMapping("/{userId}/events")
    public List<CommonSingleEventResponse> getEventByUserId(@PathVariable Long userId,
                                                            @RequestParam(defaultValue = "0") Integer from,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Запрос авторизованным пользователем событий по userId = {}, from = {}, size = {}", userId, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getEventsByUserId(userId, pageRequest);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonSingleEventResponse addEvent(@PathVariable Long userId,
                                              @RequestBody @Valid AuthorizedEventRequestDto requestDto) {
        log.info("Создание авторизованным пользователем события. userId = {}, {}", userId, requestDto);
        return service.createEvent(userId, requestDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public CommonSingleEventResponse getSingleEventById(@PathVariable Long userId,
                                                        @PathVariable Long eventId) {
        log.info("Запрос события пользователя userId = {}, eventId = {}", userId, eventId);
        return service.getUserEventByEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public CommonSingleEventResponse patchSingleEvent(@PathVariable Long userId,
                                                      @PathVariable Long eventId,
                                                      @RequestBody @Valid PatchEventRequestDto requestDto) {
        log.info("Изменение события пользователя userId = {}, eventId = {}, new event = {}", userId, eventId, requestDto);
        return service.patchEvent(userId, eventId, requestDto);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<AuthorizedRequestResponseDto> getEventRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        log.info("Запрос заявок на участие в событии пользователя userId = {}, eventId = {}", userId, eventId);
        return service.getUserEventRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(@PathVariable Long userId,
                                                                                   @PathVariable Long eventId,
                                                                                   @RequestBody @Valid AuthorizedMultipleRequestsChangeRequestDto requestDto) {
        log.info("Изменение авторизованным пользователем статусов по событиям userId = {}, eventId = {}, request = {}", userId, eventId, requestDto);
        return service.changeMultipleRequestStatus(userId, eventId, requestDto);
    }
}
