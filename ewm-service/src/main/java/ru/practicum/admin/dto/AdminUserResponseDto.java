package ru.practicum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserResponseDto {
    private Long id;
    private String name;
    private String email;
}
