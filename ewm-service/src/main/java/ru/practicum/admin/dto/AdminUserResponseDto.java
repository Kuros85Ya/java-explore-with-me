package ru.practicum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AdminUserResponseDto {
    Long id;
    String name;
    String email;
}
