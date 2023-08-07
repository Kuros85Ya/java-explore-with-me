package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AdminCompilationPostRequestDto {
    List<Long> events;
    Boolean pinned = false;
    @NotBlank
    @Size(min = 1)
    @Size(max = 50)
    String title;
}
