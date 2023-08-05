package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.admin.service.AdminCategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCategoryController {

    private final AdminCategoryService service;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommonCategoryResponseDto addCategory(@RequestBody @Valid AdminCategoryRequestDto requestDto) {
        log.info("Добавление категории администратором {}", requestDto);
        return service.addCategory(requestDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
    log.info("Удаление администратором категории {}", catId);
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CommonCategoryResponseDto patchCategory(@PathVariable long catId, @RequestBody @Valid AdminCategoryRequestDto requestDto) {
        log.info("Изменение администратором категории {}, новое значение {}", catId, requestDto);
        return service.patchCategory(catId, requestDto);
    }
}
