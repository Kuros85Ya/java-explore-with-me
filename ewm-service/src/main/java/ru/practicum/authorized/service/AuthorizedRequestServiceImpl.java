package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.mapper.RequestMapper;
import ru.practicum.common.enums.RequestStatus;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;
import ru.practicum.common.repository.RequestRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedRequestServiceImpl implements AuthorizedRequestService {

    private final RequestRepository requestRepository;
    private final AuthorizedEventServiceImpl eventService;

    @Override
    public List<AuthorizedRequestResponseDto> getUserRequests(Long userId) {
        User requester = eventService.findUserById(userId);
        List<Request> requests = requestRepository.findAllByRequester(requester);
        return requests
                .stream()
                .map(RequestMapper::toRequestResponseDto)
                .collect(Collectors.toList());
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
        Request request = findRequestById(requestId);
        validateToBeCancelledRequest(request, userId);
        Request modifiedRequest = RequestMapper.toChangedStatusPatchRequest(request, RequestStatus.CANCELED);
        requestRepository.save(modifiedRequest);
        return RequestMapper.toRequestResponseDto(modifiedRequest);
    }

    private void validateToBeCancelledRequest(Request request, Long userId) {
        if (!Objects.equals(request.getRequester().getId(), userId)) throw new ValidationException("Этот пользователь не может изменять запрос");
    }

    public Request findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Запрос с ID = " + id + " не найден."));
    }


}
