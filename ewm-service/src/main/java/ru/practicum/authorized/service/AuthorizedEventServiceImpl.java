package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeRequestDto;
import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.authorized.mapper.RequestMapper;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.model.Category;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;
import ru.practicum.common.repository.CategoryRepository;
import ru.practicum.common.repository.EventRepository;
import ru.practicum.common.repository.RequestRepository;
import ru.practicum.common.repository.UserRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedEventServiceImpl implements AuthorizedEventService {

    private final EventRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<CommonSingleEventResponse> getEventsByUserId(Long id, PageRequest pageRequest) {
        User creator = findUserById(id);
        List<Event> events = repository.getEventsByCreatorIs(creator, pageRequest);
        return events
                .stream()
                .map(EventMapper::toEventResponseDto)
                .collect(Collectors.toList());
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
        User creator = findUserById(userId);
        Event event = repository.getEventByIdAndCreator(eventId, creator);
        return EventMapper.toEventResponseDto(event);
    }

    @Override
    public CommonSingleEventResponse patchEvent(Long userId, Long eventId, PatchEventRequestDto requestDto) {
        Event event = findEventById(eventId);
        validateEventBeforePatching(userId, event);
        Category category;

        if (requestDto.getCategory() == null) {
            category = null;
        } else {
            category = findCategoryById(requestDto.getCategory());
        }

        Event modifiedEvent = EventMapper.patchRequestToEvent(event, category, requestDto);
        return EventMapper.toEventResponseDto(modifiedEvent);
    }

    @Override
    public List<AuthorizedRequestResponseDto> getUserEventRequest(Long userId, Long eventId) {
        Event event = findEventById(eventId);
        validateUserIsCreator(userId, event);
        List<Request> requests = requestRepository.findAllByEvent(event);
        return requests
                .stream()
                .map(RequestMapper::toRequestResponseDto)
                .collect(Collectors.toList());
    }

    /** Изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409)
     дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409) **/
    private void validateEventBeforePatching(Long userId, Event event) {
        if ((!Objects.equals(event.getCreator().getId(), userId)
            || (Objects.equals(event.getStatus(), EventState.PUBLISHED.name())))
            || (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))) {
            throw new ValidationException("Это событие не может быть изменено пользователем");
        }
    }

    /**если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
     статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
     если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить**/

    //todo avtuman1 Надо дописать валидацию. Тут может быть хитро - можно ли аппрувнуть только часть заявок?
    private void validateRequestBeforeChangingStatus(Long userId, Event event, Request request) {
//        if ((!Objects.equals(r.getCreator().getId(), userId)
//                || (Objects.equals(event.getStatus(), EventState.PUBLISHED.name())))
//                || (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))) {
//            throw new ValidationException("Это событие не может быть изменено пользователем");
//        }
    }

    @Override
    public AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(Long userId, Long eventId, AuthorizedMultipleRequestsChangeRequestDto requestDto) {
        Event event = findEventById(eventId);
        List<Request> requests = requestRepository.findAllByEvent(event);
        requests.forEach(it -> validateRequestBeforeChangingStatus(userId, event, it));
        List<Request> modifiedRequests = requests.stream()
                .map(it -> RequestMapper.toChangedStatusPatchRequest(it, requestDto.getStatus()))
                .collect(Collectors.toList());
        requestRepository.saveAll(modifiedRequests);
        return RequestMapper.toChangedStatusResponseDto(modifiedRequests);
    }

    private void validateUserIsCreator(Long userId, Event event) {
        if (!Objects.equals(userId, event.getCreator().getId())) throw new ValidationException("Пользователь " + userId +" не является создателем события "+ event.getId());
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
