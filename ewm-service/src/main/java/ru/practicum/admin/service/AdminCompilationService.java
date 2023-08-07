package ru.practicum.admin.service;

import ru.practicum.admin.dto.*;
import ru.practicum.common.dto.CompilationResponseDto;

public interface AdminCompilationService {

    CompilationResponseDto addCompilation(AdminCompilationRequestDto requestDto);
    void deleteCompilation(Long compId);
    CompilationResponseDto patchCompilation(Long compId, AdminCompilationRequestDto requestDto);
}
