package ru.practicum.common.mapper;

import ru.practicum.admin.dto.AdminEventInitiator;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.dto.Location;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.enums.RequestStatus;
import ru.practicum.common.enums.StateAction;
import ru.practicum.common.model.Category;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Objects;

import static ru.practicum.common.Utils.dttmToString;
import static ru.practicum.common.Utils.parseDttm;

public class EventMapper {

    public static Event patchRequestToEvent(Event event, Category category, PatchEventRequestDto requestDto, ru.practicum.common.model.Location location) {
        EventState newState;
        if (requestDto.getStateAction() == StateAction.PUBLISH_EVENT) {
            newState = EventState.PUBLISHED;
        } else if (requestDto.getStateAction() == StateAction.REJECT_EVENT || requestDto.getStateAction() == StateAction.CANCEL_REVIEW) {
            newState = EventState.CANCELED;
        } else if (requestDto.getStateAction() == StateAction.SEND_TO_REVIEW) {
            newState = EventState.PENDING;
        } else {
            newState = EventState.valueOf(event.getStatus());
        }

        LocalDateTime publishedOn;
        if (newState == EventState.PUBLISHED) {
            publishedOn = LocalDateTime.now();
        } else {
            publishedOn = null;
        }

        String annotation;
        if (requestDto.getAnnotation() == null) {
            annotation = event.getAnnotation();
        } else {
            annotation = requestDto.getAnnotation();
        }

        String description;

        if (requestDto.getDescription() == null) {
            description = event.getDescription();
        } else {
            description = requestDto.getDescription();
        }

        LocalDateTime eventDate;
        if (requestDto.getEventDate() == null) {
            eventDate = event.getEventDate();
        } else {
            eventDate = parseDttm(requestDto.getEventDate());
            if (eventDate.isBefore(LocalDateTime.now())) {
                throw new ValidationException("Новая дата в прошлом");
            }
        }

        ru.practicum.common.model.Location newLocation;
        if (location == null) {
            newLocation = event.getLocation();
        } else {
            newLocation = location;
        }

        Boolean paid;
        if (requestDto.getPaid() == null) {
            paid = event.getPaid();
        } else {
            paid = requestDto.getPaid();
        }

        Integer limit;
        if (requestDto.getParticipantLimit() == null) {
            limit = event.getParticipantLimit();
        } else {
            limit = requestDto.getParticipantLimit();
        }

        Boolean requestModeration;
        if (requestDto.getRequestModeration() == null) {
            requestModeration = event.getRequestModeration();
        } else {
            requestModeration = requestDto.getRequestModeration();
        }

        Category categoryModified;
        if (category == null) {
            categoryModified = event.getCategory();
        } else {
            categoryModified = category;
        }

        String title;
        if (requestDto.getTitle() == null) {
            title = event.getTitle();
        } else {
            title = requestDto.getTitle();
        }

        return new Event(
                event.getId(),
                annotation,
                description,
                eventDate,
                newLocation,
                paid,
                limit,
                requestModeration,
                title,
                categoryModified,
                event.getCreator(),
                event.getCreatedOn(),
                publishedOn,
                newState.name(),
                event.getCompilations(),
                event.getRequests()
        );
    }

    public static Event toEvent(User user, Category category, AuthorizedEventRequestDto requestDto, ru.practicum.common.model.Location location) {
        LocalDateTime eventDate = parseDttm(requestDto.getEventDate());
        if (eventDate.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата события не может быть в прошлом");
        }

        EventState state = EventState.PENDING;

        return new Event(
                null,
                requestDto.getAnnotation(),
                requestDto.getDescription(),
                eventDate,
                location,
                requestDto.getPaid(),
                requestDto.getParticipantLimit(),
                requestDto.getRequestModeration(),
                requestDto.getTitle(),
                category,
                user,
                LocalDateTime.now(),
                null,
                state.name(),
                null,
                null
        );
    }

    public static CommonSingleEventResponse toEventResponseDto(Event event, Long views) {
        long confirmedRequests;
        if (event.getRequests() == null) {
            confirmedRequests = 0L;
        } else {
            confirmedRequests = event.getRequests().stream().filter(it -> Objects.equals(it.getStatus(), RequestStatus.CONFIRMED.name())).count();
        }

        return new CommonSingleEventResponse(
                event.getAnnotation(),
                event.getCategory(),
                confirmedRequests,
                dttmToString(event.getCreatedOn()),
                event.getDescription(),
                dttmToString(event.getEventDate()),
                event.getId(),
                new AdminEventInitiator(event.getCreator().getId(), event.getCreator().getName()),
                new Location(event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                event.getPaid(),
                event.getParticipantLimit(),
                dttmToString(event.getPublishedOn()),
                event.getRequestModeration(),
                EventState.valueOf(event.getStatus()),
                event.getTitle(),
                views
        );
    }
}
