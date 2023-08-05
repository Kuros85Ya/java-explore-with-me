package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

@Data
public class AdminCompilationResponseDto {
    List<CommonSingleEventResponse> events;
    Long id;
    Boolean pinned;
    String title;
}
