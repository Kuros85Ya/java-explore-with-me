package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedRequestServiceImpl implements AuthorizedRequestService {

    @Override
    public List<AuthorizedRequestResponseDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public AuthorizedRequestResponseDto addRequestToJoinEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public AuthorizedRequestResponseDto cancelRequestById(Long userId, Long requestId) {
        return null;
    }
}
