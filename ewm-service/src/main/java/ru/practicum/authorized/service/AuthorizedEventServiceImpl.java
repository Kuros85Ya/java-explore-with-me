package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedEventServiceImpl implements AuthorizedEventService {

    @Override
    public List<CommonSingleEventResponse> getEventsByUserId(Long id, PageRequest pageRequest) {
        return null;
    }

    @Override
    public CommonSingleEventResponse createEvent(Long userId, AuthorizedEventRequestDto requestDto) {
        return null;
    }

    @Override
    public CommonSingleEventResponse getUserEventByEventId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public CommonSingleEventResponse patchEvent(Long userId, Long eventId, AuthorizedEventRequestDto requestDto) {
        return null;
    }

    @Override
    public List<AuthorizedRequestResponseDto> getEventRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(Long userId, Long eventId, AuthorizedMultipleRequestsChangeRequestDto requestDto) {
        return null;
    }
}
