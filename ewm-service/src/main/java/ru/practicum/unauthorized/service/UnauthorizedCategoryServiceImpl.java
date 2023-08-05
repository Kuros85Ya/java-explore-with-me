package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CommonCategoryResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedCategoryServiceImpl implements UnauthorizedCategoryService {

    @Override
    public List<CommonCategoryResponseDto> getCategories(PageRequest pageRequest) {
        return null;
    }

    @Override
    public CommonCategoryResponseDto getCategoryById(Long id) {
        return null;
    }
}
