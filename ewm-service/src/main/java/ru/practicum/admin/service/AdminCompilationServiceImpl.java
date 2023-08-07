package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.*;
import ru.practicum.admin.mapper.CompilationMapper;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.common.model.Compilation;
import ru.practicum.common.model.Event;
import ru.practicum.common.repository.CompilationRepository;
import ru.practicum.common.repository.EventRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CompilationResponseDto addCompilation(AdminCompilationRequestDto requestDto) {
        List<Event> eventList = eventRepository.getAllByIdIn(requestDto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(requestDto, eventList);
        repository.save(compilation);
        return CompilationMapper.toCompilationResponseDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public CompilationResponseDto patchCompilation(Long compId, AdminCompilationRequestDto requestDto) {
        Compilation compilation = repository.findById(compId).orElseThrow(()
                -> new NoSuchElementException("Компилция с ID = " + compId + " не найдена."));
        List<Event> eventList = eventRepository.getAllByIdIn(requestDto.getEvents());

        Compilation modifiedCompilation = CompilationMapper.patchCompilation(compilation, requestDto, eventList);
        repository.save(modifiedCompilation);
        return CompilationMapper.toCompilationResponseDto(modifiedCompilation);
    }
}
