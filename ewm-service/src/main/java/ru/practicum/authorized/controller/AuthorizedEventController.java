package ru.practicum.authorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.service.AuthorizedEventService;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.mapper.RequestMapper;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthorizedEventController {

    private final AuthorizedEventService service;

    @GetMapping("/{userId}/events")
    public List<CommonSingleEventResponse> getEventById(@PathVariable Long userId,
                                                        @RequestParam Integer from,
                                                        @RequestParam Integer size) {
        log.info("Запрос авторизованным пользователем событий по userId = {}, from = {}, size = {}", userId, from, size);
        PageRequest pageRequest = RequestMapper.toPageRequest(from, size);
        return service.getEventsByUserId(userId, pageRequest);
    }

    @PostMapping("/{userId}/events")
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
                                                      @RequestBody @Valid AuthorizedEventRequestDto requestDto) {
        log.info("Изменение события пользователя userId = {}, eventId = {}, new event = {}", userId, eventId, requestDto);
        return service.patchEvent(userId, eventId, requestDto);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<AuthorizedRequestResponseDto> getEventRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        log.info("Запрос авторизованным пользователем по событиям userId = {}, eventId = {}", userId, eventId);
        return service.getEventRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(@PathVariable Long userId,
                                                                                   @PathVariable Long eventId,
                                                                                   @RequestBody @Valid AuthorizedMultipleRequestsChangeRequestDto requestDto) {
        log.info("Изменение авторизованным пользователем статусов по событиям userId = {}, eventId = {}, request = {}", userId, eventId, requestDto);
        return service.changeMultipleRequestStatus(userId, eventId, requestDto);
    }
}
