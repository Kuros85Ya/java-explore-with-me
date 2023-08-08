package ru.practicum.admin.service;

import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.common.dto.CommonCategoryResponseDto;

public interface AdminCategoryService {

    /**
     * Добавление категории событий. Имя категории должно быть уникальным
     **/
    CommonCategoryResponseDto addCategory(AdminCategoryRequestDto requestDto);

    void deleteCategory(Long catId);

    CommonCategoryResponseDto patchCategory(Long catId, AdminCategoryRequestDto requestDto);
}
