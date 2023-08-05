package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminUserRequestDto;
import ru.practicum.admin.dto.AdminUserResponseDto;
import ru.practicum.admin.dto.AdminSingleUserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUsersService {

    @Override
    public List<AdminSingleUserResponse> getUsers(List<Integer> ids, PageRequest pageRequest) {
        return null;
    }

    @Override
    public AdminUserResponseDto addUser(AdminUserRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
