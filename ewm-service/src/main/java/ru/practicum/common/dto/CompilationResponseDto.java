package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationResponseDto {
    List<CommonSingleEventResponse> events;
    Long id;
    Boolean pinned;
    String title;
}
