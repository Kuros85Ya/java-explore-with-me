package ru.practicum.unauthorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.unauthorized.dto.UnauthorizedCompilationResponseDto;

import java.util.List;

public interface UnauthorizedCompilationService {

    List<UnauthorizedCompilationResponseDto> getCompilations(Boolean pinned, PageRequest pageRequest);
    UnauthorizedCompilationResponseDto getCompilationById(Long compId);
}
