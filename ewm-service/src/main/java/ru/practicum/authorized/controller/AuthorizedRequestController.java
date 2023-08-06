package ru.practicum.authorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.service.AuthorizedRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthorizedRequestController {

    private final AuthorizedRequestService service;

    @GetMapping
    public List<AuthorizedRequestResponseDto> getEventById(@PathVariable Long userId) {
        log.info("Запрос авторизованным пользователем информации о его заявках в чужих событиях, userId = {}", userId);
        return service.getUserRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorizedRequestResponseDto addRequestToJoinEvent(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Добавление заявки авторизованного пользователя на участие в событии, userId = {}, eventId = {}", userId, eventId);
        return service.addRequestToJoinEvent(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public AuthorizedRequestResponseDto cancelOwnRequestToJoinEvent(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Отмена заявки авторизованного пользователя на участие в событии, userId = {}, requestId = {}", userId, requestId);
        return service.cancelRequestById(userId, requestId);
    }
}
