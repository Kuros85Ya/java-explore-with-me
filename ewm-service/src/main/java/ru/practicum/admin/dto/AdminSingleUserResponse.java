package ru.practicum.admin.dto;

import lombok.Data;

@Data
public class AdminSingleUserResponse {
    String email;
    Long id;
    String name;
}
