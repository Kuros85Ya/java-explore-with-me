package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonCategoryResponseDto {
    Long id;
    String name;
}
