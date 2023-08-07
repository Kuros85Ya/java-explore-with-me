package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.admin.mapper.CompilationMapper;
import ru.practicum.common.model.Compilation;
import ru.practicum.common.repository.CompilationRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedCompilationServiceImpl implements UnauthorizedCompilationService {

    private final CompilationRepository repository;

    @Override
    public List<CompilationResponseDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = repository.findAll(pageRequest).toList();
        } else {
            compilations = repository.findAllByPinned(pinned, pageRequest);
        }

        return compilations
                .stream()
                .map(CompilationMapper::toCompilationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponseDto getCompilationById(Long compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(()
                -> new NoSuchElementException("Компилция с ID = " + compId + " не найдена."));
        return CompilationMapper.toCompilationResponseDto(compilation);
    }
}
