package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    @Override
    public AdminCompilationResponseDto addCompilation(AdminCompilationRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Long compId) {

    }

    @Override
    public AdminCompilationResponseDto patchCompilation(Long compId, AdminCompilationRequestDto requestDto) {
        return null;
    }
}
