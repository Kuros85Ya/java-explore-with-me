package ru.practicum.authorized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.common.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthorizedRequestResponseDto {
    LocalDateTime created;
    Long event;
    Long id;
    Long requester;
    RequestStatus status;
}
