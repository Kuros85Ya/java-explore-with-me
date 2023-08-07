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
import ru.practicum.unauthorized.service.StatsService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository repository;
    private final EventRepository eventRepository;
    private final StatsService service;

    @Override
    public CompilationResponseDto addCompilation(AdminCompilationPostRequestDto requestDto) {
        List<Event> eventList;
        if (requestDto.getEvents() == null) {
            eventList = Collections.emptyList();
        } else {
            eventList = eventRepository.getAllByIdIn(requestDto.getEvents());
        }
        Compilation compilation = CompilationMapper.toCompilation(requestDto, eventList);
        repository.save(compilation);
        service.getListEventViews(eventList);
        Map<Long, Long> eventViews = service.getListEventViews(eventList);
        return CompilationMapper.toCompilationResponseDto(compilation, eventViews);
    }

    @Override
    public void deleteCompilation(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public CompilationResponseDto patchCompilation(Long compId, AdminCompilationPatchRequestDto requestDto) {
        Compilation compilation = repository.findById(compId).orElseThrow(()
                -> new NoSuchElementException("Компилция с ID = " + compId + " не найдена."));

        List<Event> eventList;
        if (requestDto.getEvents() == null) {
            eventList = Collections.emptyList();
        } else {
            eventList = eventRepository.getAllByIdIn(requestDto.getEvents());
        }
        Compilation modifiedCompilation = CompilationMapper.patchCompilation(compilation, requestDto, eventList);
        repository.save(modifiedCompilation);
        Map<Long, Long> eventViews = service.getListEventViews(eventList);
        return CompilationMapper.toCompilationResponseDto(modifiedCompilation, eventViews);
    }
}
