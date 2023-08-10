package ru.practicum.authorized.service;

import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;

import java.util.List;

public interface AuthorizedRequestService {
    List<AuthorizedRequestResponseDto> getUserRequests(Long userId);

    AuthorizedRequestResponseDto addRequestToJoinEvent(Long userId, Long eventId);

    AuthorizedRequestResponseDto cancelRequestById(Long userId, Long requestId);

}

