package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminUserRequestDto;
import ru.practicum.admin.dto.AdminUserResponseDto;
import ru.practicum.admin.mapper.UserMapper;
import ru.practicum.common.model.User;
import ru.practicum.common.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUsersService {

    private final UserRepository repository;

    @Override
    public List<AdminUserResponseDto> getUsers(List<Long> ids, PageRequest pageRequest) {
        List<User> users;
        if (ids == null || ids.isEmpty()) {
            users = repository.findAll(pageRequest).toList();
        } else {
            users = repository.getUsersByIdsFromList(ids, pageRequest);
        }
        return users.stream().map(UserMapper::toUserResponseDto).collect(Collectors.toList());
    }

    @Override
    public AdminUserResponseDto addUser(AdminUserRequestDto requestDto) {
        User user = UserMapper.toUser(null, requestDto);
        return UserMapper.toUserResponseDto(repository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }
}
