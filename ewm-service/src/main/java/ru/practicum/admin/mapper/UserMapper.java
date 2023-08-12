package ru.practicum.admin.mapper;

import ru.practicum.admin.dto.AdminUserRequestDto;
import ru.practicum.admin.dto.AdminUserResponseDto;
import ru.practicum.common.model.User;

public class UserMapper {

    public static User toUser(Long id, AdminUserRequestDto user) {
        return new User(
                id,
                user.getName(),
                user.getEmail(),
                null,
                null
        );
    }

    public static AdminUserResponseDto toUserResponseDto(User user) {
        return new AdminUserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
