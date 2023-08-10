package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonCategoryResponseDto {
    private Long id;
    private String name;
}
