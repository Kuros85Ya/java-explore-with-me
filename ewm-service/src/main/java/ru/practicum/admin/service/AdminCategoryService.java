package ru.practicum.admin.service;

import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.admin.dto.AdminCategoryResponseDto;

public interface AdminCategoryService {

    /**Добавление категории событий. Имя категории должно быть уникальным**/
    AdminCategoryResponseDto addCategory(AdminCategoryRequestDto requestDto);
    void deleteCategory(Long catId);
    AdminCategoryResponseDto patchCategory(Long catId, AdminCategoryRequestDto requestDto);
}
