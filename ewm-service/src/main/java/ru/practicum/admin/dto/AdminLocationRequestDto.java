package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdminLocationRequestDto {
    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;
    @Size(min = 2)
    @Size(max = 300)
    private String description;
}
