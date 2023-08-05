package ru.practicum.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.*;
import ru.practicum.common.model.User;

import java.util.List;

public interface AdminUsersService {
    List<AdminUserResponseDto> getUsers(List<Long> ids, PageRequest pageRequest);

    AdminUserResponseDto addUser(AdminUserRequestDto requestDto);

    void deleteUser(Long userId);
}
