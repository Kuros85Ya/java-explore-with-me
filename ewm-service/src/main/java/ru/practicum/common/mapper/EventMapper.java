package ru.practicum.common.mapper;

import ru.practicum.admin.dto.AdminEventInitiator;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.authorized.dto.AuthorizedEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.dto.Location;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.enums.StateAction;
import ru.practicum.common.model.Category;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.User;

import java.time.LocalDateTime;

import static ru.practicum.common.util.dttmToString;
import static ru.practicum.common.util.parseDttm;

public class EventMapper {

    public static Event patchRequestToEvent(Event event, Category category, AdminPatchEventRequestDto requestDto) {
        EventState newState;
        if (requestDto.getStateAction() == StateAction.PUBLISH_EVENT) {
            newState = EventState.PUBLISHED;
        } else if (requestDto.getStateAction() == StateAction.REJECT_EVENT) {
            newState = EventState.CANCELED;
        } else newState = EventState.valueOf(event.getStatus());

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
        }

        Float latitude;
        if (requestDto.getLocation() == null || requestDto.getLocation().getLat() == null) {
            latitude = event.getLocationLatitude();
        } else {
            latitude = requestDto.getLocation().getLat();
        }

        Float longitude;
        if (requestDto.getLocation() == null || requestDto.getLocation().getLon() == null) {
            longitude = event.getLocationLongitude();
        } else longitude = requestDto.getLocation().getLon();

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

        String title;
        if (requestDto.getTitle() == null) {title = event.getTitle();}
        else {title = requestDto.getTitle();}

        return new Event(
                event.getId(),
                annotation,
                description,
                eventDate,
                latitude,
                longitude,
                paid,
                limit,
                requestModeration,
                title,
                category,
                event.getCreator(),
                event.getCreatedOn(),
                publishedOn,
                newState.name()
        );
    }

    public static Event toEvent(User user, Category category, AuthorizedEventRequestDto requestDto) {
        LocalDateTime eventDate = parseDttm(requestDto.getEventDate());
        EventState state;
        if (requestDto.getRequestModeration()) {
            state = EventState.PENDING;
        } else {
            state = EventState.PUBLISHED;
        }

        return new Event(
                null,
                requestDto.getAnnotation(),
                requestDto.getDescription(),
                eventDate,
                requestDto.getLocation().getLat(),
                requestDto.getLocation().getLon(),
                requestDto.getPaid(),
                requestDto.getParticipantLimit(),
                requestDto.getRequestModeration(),
                requestDto.getTitle(),
                category,
                user,
                LocalDateTime.now(),
                null,
                state.name()
        );
    }

    public static CommonSingleEventResponse toEventResponseDto(Event event) {
        return new CommonSingleEventResponse(
                event.getAnnotation(),
                event.getCategory(),
                null, //todo avtuman1 Когда добавятся реквесты, здесь появится метрика confirmedRequests
                dttmToString(event.getCreatedOn()),
                event.getDescription(),
                dttmToString(event.getEventDate()),
                event.getId(),
                new AdminEventInitiator(event.getCreator().getId(), event.getCreator().getName()),
                new Location(event.getLocationLatitude(), event.getLocationLongitude()),
                event.getPaid(),
                event.getParticipantLimit(),
                dttmToString(event.getPublishedOn()),
                event.getRequestModeration(),
                null, //todo avtuman1 нужно добавить state
                event.getTitle(),
                null //todo avtuman1 нужно добавить метрику views
        );
    }
}
