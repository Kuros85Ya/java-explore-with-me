package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationResponseDto {
    private List<CommonSingleEventResponse> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
