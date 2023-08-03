package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.*;
import ru.practicum.admin.mapper.RequestMapper;
import ru.practicum.admin.service.AdminUsersService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final AdminUsersService service;

    @GetMapping()
    public List<SingleAdminUserResponse> getUsers(
            @RequestParam List<Integer> ids,
            @RequestParam Integer from,
            @RequestParam Integer size) {
        log.info("Поиск пользователей по параметрам {} {} {}", ids, from, size);
        PageRequest pageRequest = RequestMapper.toPageRequest(from, size);
        return service.getUsers(ids, pageRequest);
    }

    @PostMapping()
    public AdminUserResponseDto addUser(@RequestBody @Valid AdminUserRequestDto requestDto) {
        log.info("Добавление администратором пользователя {}", requestDto);
        return service.addUser(requestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
