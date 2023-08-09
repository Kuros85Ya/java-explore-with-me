package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AdminCompilationPatchRequestDto {
    private List<Long> events;
    private Boolean pinned = false;
    @Size(min = 1)
    @Size(max = 50)
    private String title;
}
