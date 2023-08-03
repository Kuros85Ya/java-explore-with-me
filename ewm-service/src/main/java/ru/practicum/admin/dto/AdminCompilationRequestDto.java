package ru.practicum.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminCompilationRequestDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
