package ru.practicum.authorized.mapper;

import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.common.enums.RequestStatus;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;

import java.time.LocalDateTime;

public class RequestMapper {

    public static Request toRequest(User user, Event event) {
        return new Request(null,
                user,
                event,
                RequestStatus.PENDING,
                LocalDateTime.now());
    }

    public static AuthorizedRequestResponseDto toRequestResponseDto(Request request) {
        return new AuthorizedRequestResponseDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
