package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.unauthorized.service.UnauthorizedCategoryService;

import java.util.List;

import static ru.practicum.common.Utils.toPageRequest;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedCategoryController {

    private final UnauthorizedCategoryService service;

    @GetMapping
    public List<CommonCategoryResponseDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        log.info("Неавторизованный запрос категорий постранично from = {}, size = {}", from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getCategories(pageRequest);
    }

    @GetMapping("/{catId}")
    public CommonCategoryResponseDto getCategoryById(@PathVariable Long catId) {
        log.info("Неавторизованный запрос категории по id = {}", catId);
        return service.getCategoryById(catId);
    }
}
