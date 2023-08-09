package ru.practicum.admin.service;

import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.common.dto.CommonCategoryResponseDto;

public interface AdminCategoryService {

    CommonCategoryResponseDto addCategory(AdminCategoryRequestDto requestDto);

    void deleteCategory(Long catId);

    CommonCategoryResponseDto patchCategory(Long catId, AdminCategoryRequestDto requestDto);
}
