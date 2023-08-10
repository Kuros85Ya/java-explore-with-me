package ru.practicum.admin.mapper;

import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.common.model.Category;

public class CategoryMapper {

    public static Category toCategory(Long id, AdminCategoryRequestDto category) {
        return new Category(
                id,
                category.getName()
        );
    }

    public static CommonCategoryResponseDto toCategoryResponseDto(Category category) {
        return new CommonCategoryResponseDto(category.getId(), category.getName());
    }
}
