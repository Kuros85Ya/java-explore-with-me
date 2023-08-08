package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.*;
import ru.practicum.admin.service.AdminUsersService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.common.Utils.toPageRequest;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUserController {

    private final AdminUsersService service;

    @GetMapping
    public List<AdminUserResponseDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Поиск пользователей по параметрам {} {} {}", ids, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getUsers(ids, pageRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdminUserResponseDto createUser(@RequestBody @Valid AdminUserRequestDto requestDto) {
        log.info("Добавление пользователя администратором {}", requestDto);
        return service.addUser(requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Удаление пользователя userId = {}", userId);
        service.deleteUser(userId);
    }
}
