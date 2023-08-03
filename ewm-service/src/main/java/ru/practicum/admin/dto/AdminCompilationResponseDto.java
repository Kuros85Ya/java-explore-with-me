package ru.practicum.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminCompilationResponseDto {
    List<SingleAdminEventResponse> events;
    Long id;
    Boolean pinned;
    String title;
}
