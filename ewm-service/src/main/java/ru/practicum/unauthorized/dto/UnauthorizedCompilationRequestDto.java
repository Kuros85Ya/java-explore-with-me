package ru.practicum.unauthorized.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnauthorizedCompilationRequestDto {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}
