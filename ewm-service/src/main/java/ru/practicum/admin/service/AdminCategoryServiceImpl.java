package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminCategoryRequestDto;
import ru.practicum.common.dto.CommonCategoryResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Override
    public CommonCategoryResponseDto addCategory(AdminCategoryRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long catId) {

    }

    @Override
    public CommonCategoryResponseDto patchCategory(Long catId, AdminCategoryRequestDto requestDto) {
        return null;
    }
}
