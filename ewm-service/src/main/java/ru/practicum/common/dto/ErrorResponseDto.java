package ru.practicum.common.dto;

import lombok.Data;
import ru.practicum.common.enums.ErrorStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    private final ErrorStatus status;
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp;
}