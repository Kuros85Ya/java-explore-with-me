package ru.practicum.authorized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.common.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthorizedRequestResponseDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private RequestStatus status;
}
