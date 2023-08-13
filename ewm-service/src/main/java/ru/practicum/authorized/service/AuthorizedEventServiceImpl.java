package ru.practicum.authorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
import ru.practicum.common.enums.RequestStatus;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.mapper.LocationMapper;
import ru.practicum.common.model.*;
import ru.practicum.common.repository.*;
import ru.practicum.unauthorized.service.StatsService;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizedEventServiceImpl implements AuthorizedEventService {

    private final EventRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final StatsService service;

    @Override
    public List<CommonSingleEventResponse> getEventsByUserId(Long id, PageRequest pageRequest) {
        User creator = findUserById(id);
        List<Event> events = repository.getEventsByCreatorIs(creator, pageRequest);

        Map<Long, Long> eventViews = service.getListEventViews(events);
        return events
                .stream()
                .map(it -> EventMapper.toEventResponseDto(it, eventViews.get(it.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommonSingleEventResponse> getEventsByUserLocation(Long userId, Boolean isNearFavorite, Boolean isNearLastVisited, Long maxValue, PageRequest pageRequest) {
        User creator = findUserById(userId);
        AverageLocation location = getLocationOfInterestByUserId(creator.getId(), isNearFavorite, isNearLastVisited);
        List<Event> events = repository.getEventsByLocationPageable(location.getLatitude().floatValue(), location.getLongitude().floatValue(), maxValue, pageRequest);
        Map<Long, Long> eventViews = service.getListEventViews(events);
        return events
                .stream()
                .map(it -> EventMapper.toEventResponseDto(it, eventViews.get(it.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public CommonSingleEventResponse createEvent(Long userId, AuthorizedEventRequestDto requestDto) {
        User user = findUserById(userId);
        Category category = findCategoryById(requestDto.getCategory());
        Location newLocation = getLocation(requestDto.getLocation());

        Event event = repository.save(EventMapper.toEvent(user, category, requestDto, newLocation));
        return EventMapper.toEventResponseDto(event, 0L);
    }

    public Location getLocation(ru.practicum.common.dto.Location location) {
        if (location == null) {
            return null;
        } else {
            Location existingLocation = locationRepository.getLocationByLatitudeAndLongitude(location.getLat(), location.getLon());
            return Objects.requireNonNullElseGet(existingLocation, () -> locationRepository.save(LocationMapper.toLocation(location)));
        }
    }

    @Override
    public CommonSingleEventResponse getUserEventByEventId(Long userId, Long eventId) {
        User creator = findUserById(userId);
        Event event = repository.getEventByIdAndCreator(eventId, creator);
        Long views = service.getEventView(event);
        return EventMapper.toEventResponseDto(event, views);
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

        Location location = getLocation(requestDto.getLocation());

        Event modifiedEvent = EventMapper.patchRequestToEvent(event, category, requestDto, location);
        Long views = service.getEventView(modifiedEvent);
        return EventMapper.toEventResponseDto(modifiedEvent, views);
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

    /**
     * Изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409)
     * дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409)
     **/
    private void validateEventBeforePatching(Long userId, Event event) {
        if ((!Objects.equals(event.getCreator().getId(), userId)
                || (Objects.equals(event.getStatus(), EventState.PUBLISHED.name())))
                || (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))) {
            throw new DataIntegrityViolationException("Это событие не может быть изменено пользователем");
        }
    }

    /**
     * если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     * нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
     * статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
     * если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
     **/
    private void validateRequestBeforeChangingStatus(Long userId, Event event, Request request) {
        if (!Objects.equals(event.getCreator().getId(), userId)
                || (Objects.equals(event.getRequestModeration(), false))
                || Objects.equals(request.getStatus(), RequestStatus.CONFIRMED.name()))
            throw new DataIntegrityViolationException("Это событие не может быть изменено пользователем");
    }

    @Override
    public AuthorizedMultipleRequestsChangeResponseDto changeMultipleRequestStatus(Long userId, Long eventId, AuthorizedMultipleRequestsChangeRequestDto requestDto) {
        Event event = findEventById(eventId);
        List<Request> requestsToApprove = requestRepository.getAllRequestsFromListOfIds(requestDto.getRequestIds());
        if (event.getParticipantLimit() != 0) {
            if (event.getRequests().stream().filter(it -> Objects.equals(it.getStatus(), RequestStatus.CONFIRMED.name())).count() >= event.getParticipantLimit()) {
                throw new DataIntegrityViolationException("Достигнуто максимально возможное количество участников события");
            }
        }
        List<Request> modifiedRequests;
        if (requestDto.getStatus() == RequestStatus.CONFIRMED) {
            modifiedRequests = dealWithRequestsListApprove(requestsToApprove, event);
        } else if (requestDto.getStatus() == RequestStatus.REJECTED) {
            modifiedRequests = dealWithRequestsListReject(requestsToApprove, event);
        } else {
            throw new ValidationException("У пользователя нет прав, чтобы присвоить заявкам указанный статус");
        }
        requestRepository.saveAll(modifiedRequests);
        return RequestMapper.toChangedStatusResponseDto(modifiedRequests);
    }

    private List<Request> dealWithRequestsListApprove(List<Request> requestsToApprove, Event event) {
        List<Request> modifiedRequests = new java.util.ArrayList<>(Collections.emptyList());
        int currentNumberOfFreeSpaces;
        if (event.getParticipantLimit() == 0) {
            currentNumberOfFreeSpaces = Integer.MAX_VALUE;
        } else {
            Integer currentNumberOfApprovedRequests = requestRepository.getCurrentNumberOfValidRequests(event.getId());
            currentNumberOfFreeSpaces = event.getParticipantLimit() - currentNumberOfApprovedRequests;
        }
        int iterator = 0;
        while (currentNumberOfFreeSpaces > 0 && iterator < requestsToApprove.size()) {
            Request request = requestsToApprove.get(iterator);
            validateRequestBeforeChangingStatus(event.getCreator().getId(), event, request);
            modifiedRequests.add(RequestMapper.toChangedStatusPatchRequest(request, RequestStatus.CONFIRMED));
            currentNumberOfFreeSpaces--;
            iterator++;
        }

        while (iterator < requestsToApprove.size()) {
            Request request = requestsToApprove.get(iterator);
            validateRequestBeforeChangingStatus(event.getCreator().getId(), event, request);
            modifiedRequests.add(RequestMapper.toChangedStatusPatchRequest(request, RequestStatus.REJECTED));
            iterator++;
        }
        return modifiedRequests;
    }

    private List<Request> dealWithRequestsListReject(List<Request> requestsToApprove, Event event) {
        List<Request> modifiedRequests = new java.util.ArrayList<>(Collections.emptyList());
        for (Request r : requestsToApprove) {
            validateRequestBeforeChangingStatus(event.getCreator().getId(), event, r);
            modifiedRequests.add(RequestMapper.toChangedStatusPatchRequest(r, RequestStatus.REJECTED));
        }
        return modifiedRequests;
    }

    private AverageLocation getLocationOfInterestByUserId(Long userId, Boolean isFavorite, Boolean isLastVisited) {
        AverageLocation averageLocation;

        if (isFavorite && isLastVisited) {
            throw new ValidationException("Нужно выбрать один признак для поиска isNearFavorite или isNearLastVisited, а не оба одновременно");
        } else if (isFavorite) {
            averageLocation = locationRepository.getFavoriteLocationByUserId(userId);
            if (averageLocation == null) {
                throw new NoSuchElementException("Нет информации о локации пользователя. Нужно отправить заявку на участие хотя бы в одном событии");
            }
        } else if (isLastVisited) {
            List<Event> lastVisitedEvents = repository.getVisitedEventsByUserId(userId);
            if (lastVisitedEvents == null) {
                throw new NoSuchElementException("Нет информации о локации пользователя. Нужно отправить заявку на участие хотя бы в одном событии");
            } else {
                Event lastVisitedEvent = lastVisitedEvents.get(0);
                averageLocation = new AverageLocation(lastVisitedEvent.getLocation().getLatitude().doubleValue(), lastVisitedEvent.getLocation().getLongitude().doubleValue());
            }
        } else {
            throw new ValidationException("Нужно выбрать хотя бы один признак для поиска isNearFavorite или isNearLastVisited");
        }
        return averageLocation;
    }

    private void validateUserIsCreator(Long userId, Event event) {
        if (!Objects.equals(userId, event.getCreator().getId()))
            throw new ValidationException("Пользователь " + userId + " не является создателем события " + event.getId());
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
