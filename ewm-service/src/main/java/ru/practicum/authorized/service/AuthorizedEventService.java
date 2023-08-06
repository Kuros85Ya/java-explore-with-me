package ru.practicum.authorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

public interface AuthorizedEventService {
    List<CommonSingleEventResponse> getEventsByUserId(Long id, PageRequest pageRequest);
    CommonSingleEventResponse createEvent(Long userId, AuthorizedEventRequestDto requestDto);
    CommonSingleEventResponse getUserEventByEventId(Long userId, Long eventId);
    CommonSingleEventResponse patchEvent(Long userId, Long eventId, PatchEventRequestDto requestDto);
    List<AuthorizedRequestResponseDto> getUserEventRequest(Long userId, Long eventId);
    AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(Long userId, Long eventId, AuthorizedMultipleRequestsChangeRequestDto requestDto);
}

