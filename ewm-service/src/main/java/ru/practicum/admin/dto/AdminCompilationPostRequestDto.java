package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AdminCompilationPostRequestDto {
    private List<Long> events;
    private Boolean pinned = false;
    @NotBlank
    @Size(min = 1)
    @Size(max = 50)
    private String title;
}
