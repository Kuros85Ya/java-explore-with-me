package ru.practicum.authorized.mapper;

import ru.practicum.authorized.dto.AuthorizedMultipleRequestsChangeResponseDto;
import ru.practicum.authorized.dto.AuthorizedRequestResponseDto;
import ru.practicum.common.enums.RequestStatus;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestMapper {

    public static Request toRequest(User user, Event event) {
        return new Request(null,
                user,
                event,
                RequestStatus.PENDING.name(),
                LocalDateTime.now());
    }

    public static AuthorizedRequestResponseDto toRequestResponseDto(Request request) {
        return new AuthorizedRequestResponseDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                RequestStatus.valueOf(request.getStatus())
        );
    }

    public static Request toChangedStatusPatchRequest(Request request, RequestStatus newStatus) {
        return new Request(request.getId(),
                request.getRequester(),
                request.getEvent(),
                newStatus.name(),
                request.getCreated());
    }

    public static AuthorizedMultipleRequestsChangeResponseDto toChangedStatusResponseDto(List<Request> requestList) {
        return new AuthorizedMultipleRequestsChangeResponseDto(
                requestList.stream().filter(it -> Objects.equals(it.getStatus(), RequestStatus.CONFIRMED.name())).map(RequestMapper::toRequestResponseDto).collect(Collectors.toList()),
                requestList.stream().filter(it -> Objects.equals(it.getStatus(), RequestStatus.REJECTED.name())).map(RequestMapper::toRequestResponseDto).collect(Collectors.toList()));
    }
}
