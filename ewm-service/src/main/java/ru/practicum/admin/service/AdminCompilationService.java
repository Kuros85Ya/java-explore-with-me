package ru.practicum.admin.service;

import ru.practicum.admin.dto.*;

public interface AdminCompilationService {

    AdminCompilationResponseDto addCompilation(AdminCompilationRequestDto requestDto);
    void deleteCompilation(Long compId);
    AdminCompilationResponseDto patchCompilation(Long compId, AdminCompilationRequestDto requestDto);
}
