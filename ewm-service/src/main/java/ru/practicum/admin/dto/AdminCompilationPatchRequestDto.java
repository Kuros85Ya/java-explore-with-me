package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AdminCompilationPatchRequestDto {
    List<Long> events;
    Boolean pinned = false;
    @Size(min = 1)
    @Size(max = 50)
    String title;
}
