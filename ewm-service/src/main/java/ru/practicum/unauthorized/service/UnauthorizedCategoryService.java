package ru.practicum.unauthorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.common.dto.CommonCategoryResponseDto;

import java.util.List;

public interface UnauthorizedCategoryService {
    List<CommonCategoryResponseDto> getCategories(PageRequest pageRequest);
    CommonCategoryResponseDto getCategoryById(Long id);

}
