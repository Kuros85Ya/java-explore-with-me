package ru.practicum.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.*;

import java.util.List;

public interface AdminUsersService {

    /**Добавление категории событий. Имя категории должно быть уникальным**/
    List<AdminSingleUserResponse> getUsers(
            List<Integer> ids,
            PageRequest pageRequest
    );

    AdminUserResponseDto addUser(AdminUserRequestDto requestDto);
    void deleteUser(Long userId);
}
