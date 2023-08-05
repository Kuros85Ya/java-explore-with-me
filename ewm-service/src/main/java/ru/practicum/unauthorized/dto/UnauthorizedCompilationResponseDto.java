package ru.practicum.unauthorized.dto;

import lombok.Data;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

@Data
public class UnauthorizedCompilationResponseDto {
    List<CommonSingleEventResponse> events;
    Long id;
    Boolean pinned;
    String title;
}
