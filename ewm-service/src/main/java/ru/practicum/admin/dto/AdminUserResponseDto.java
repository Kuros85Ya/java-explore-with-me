package ru.practicum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserResponseDto {
    Long id;
    String name;
    String email;
}
