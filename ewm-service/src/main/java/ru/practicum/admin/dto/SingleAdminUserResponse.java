package ru.practicum.admin.dto;

import lombok.Data;

@Data
public class SingleAdminUserResponse {
    String email;
    Long id;
    String name;
}
