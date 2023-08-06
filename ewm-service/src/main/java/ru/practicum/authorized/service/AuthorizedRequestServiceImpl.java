package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.mapper.RequestMapper;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;
import ru.practicum.common.repository.EventRepository;
import ru.practicum.common.repository.RequestRepository;
import ru.practicum.common.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedRequestServiceImpl implements AuthorizedRequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final AuthorizedEventServiceImpl eventService;

    @Override
    public List<AuthorizedRequestResponseDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public AuthorizedRequestResponseDto addRequestToJoinEvent(Long userId, Long eventId) {
        Event event = eventService.findEventById(eventId);
        User user = eventService.findUserById(userId);

        Request request = requestRepository.save(RequestMapper.toRequest(user, event));
        return RequestMapper.toRequestResponseDto(request);
    }

    @Override
    public AuthorizedRequestResponseDto cancelRequestById(Long userId, Long requestId) {
        return null;
    }
}
