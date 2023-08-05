package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.common.mapper.RequestMapper;
import ru.practicum.unauthorized.service.UnauthorizedCategoryService;

import java.util.List;

@Controller
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedCategoryController {

    private final UnauthorizedCategoryService service;

    @GetMapping()
    public List<CommonCategoryResponseDto> getCategories(@RequestParam Integer from, @RequestParam Integer size) {
        log.info("Неавторизованный запрос категорий постранично from = {}, size = {}", from, size);
        PageRequest pageRequest = RequestMapper.toPageRequest(from, size);
        return service.getCategories(pageRequest);
    }

    @GetMapping("/{catId}")
    public CommonCategoryResponseDto getCategoryById(@PathVariable Long catId) {
        log.info("Неавторизованный запрос категории по id = {}", catId);
        return service.getCategoryById(catId);
    }
}
