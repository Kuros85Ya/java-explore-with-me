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
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.model.Category;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.User;
import ru.practicum.common.repository.CategoryRepository;
import ru.practicum.common.repository.EventRepository;
import ru.practicum.common.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedEventServiceImpl implements AuthorizedEventService {

    private final EventRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<CommonSingleEventResponse> getEventsByUserId(Long id, PageRequest pageRequest) {
        return null;
    }

    @Override
    public CommonSingleEventResponse createEvent(Long userId, AuthorizedEventRequestDto requestDto) {
        User user = findUserById(userId);
        Category category = findCategoryById(requestDto.getCategory());

        Event event = repository.save(EventMapper.toEvent(user, category, requestDto));
        return EventMapper.toEventResponseDto(event);
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

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Пользователь с ID = " + userId + " не найден."));
    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()
                -> new NoSuchElementException("Категория с ID = " + categoryId + " не найдена."));
    }

    public Event findEventById(Long eventId) {
        return repository.findById(eventId).orElseThrow(()
            -> new NoSuchElementException("Событие с ID = " + eventId + " не найдено."));
    }
}
