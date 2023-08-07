package ru.practicum.unauthorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.common.dto.CompilationResponseDto;

import java.util.List;

public interface UnauthorizedCompilationService {

    List<CompilationResponseDto> getCompilations(Boolean pinned, PageRequest pageRequest);
    CompilationResponseDto getCompilationById(Long compId);
}
