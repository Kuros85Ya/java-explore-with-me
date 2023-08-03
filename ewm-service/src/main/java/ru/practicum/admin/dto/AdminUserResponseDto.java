package ru.practicum.admin.dto;

import lombok.Data;

@Data
public class AdminUserResponseDto {
    String email;
    Long id;
    String name;
}
