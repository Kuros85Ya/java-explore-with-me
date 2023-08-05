package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.unauthorized.dto.UnauthorizedCompilationResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedCompilationServiceImpl implements UnauthorizedCompilationService {
    @Override
    public List<UnauthorizedCompilationResponseDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        return null;
    }

    @Override
    public UnauthorizedCompilationResponseDto getCompilationById(Long compId) {
        return null;
    }
}
