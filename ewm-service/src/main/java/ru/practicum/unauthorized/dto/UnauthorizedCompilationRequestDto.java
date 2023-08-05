package ru.practicum.unauthorized.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnauthorizedCompilationRequestDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
