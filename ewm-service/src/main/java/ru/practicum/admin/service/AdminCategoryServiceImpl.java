package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.admin.mapper.CategoryMapper;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.common.model.Category;
import ru.practicum.common.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;

    @Override
    public CommonCategoryResponseDto addCategory(AdminCategoryRequestDto requestDto) {
        Category category = repository.save(CategoryMapper.toCategory(null, requestDto));
        return CategoryMapper.toCategoryResponseDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        repository.deleteById(catId);
    }

    @Override
    public CommonCategoryResponseDto patchCategory(Long catId, AdminCategoryRequestDto requestDto) {
        Category category = repository.save(CategoryMapper.toCategory(catId, requestDto));
        return CategoryMapper.toCategoryResponseDto(category);
    }
}
