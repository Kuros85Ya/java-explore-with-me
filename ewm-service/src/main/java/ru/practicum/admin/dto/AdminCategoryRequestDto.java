package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdminCategoryRequestDto {
    @Size(min = 1)
    @Size(max = 50)
    @NotBlank
    private String name;
}
