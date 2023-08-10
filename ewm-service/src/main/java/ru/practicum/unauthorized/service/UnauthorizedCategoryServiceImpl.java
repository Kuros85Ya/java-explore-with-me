package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.mapper.CategoryMapper;
import ru.practicum.common.dto.CommonCategoryResponseDto;
import ru.practicum.common.model.Category;
import ru.practicum.common.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedCategoryServiceImpl implements UnauthorizedCategoryService {

    private final CategoryRepository repository;

    @Override
    public List<CommonCategoryResponseDto> getCategories(PageRequest pageRequest) {
        List<Category> response = repository.findAll(pageRequest).toList();
        return response
                .stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommonCategoryResponseDto getCategoryById(Long id) {
        Category category = repository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Сущность с ID = " + id + " не найдена."));

        return CategoryMapper.toCategoryResponseDto(category);
    }
}
