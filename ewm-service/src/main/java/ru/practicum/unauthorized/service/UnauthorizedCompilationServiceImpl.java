package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.admin.mapper.CompilationMapper;
import ru.practicum.common.model.Compilation;
import ru.practicum.common.model.Event;
import ru.practicum.common.repository.CompilationRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedCompilationServiceImpl implements UnauthorizedCompilationService {

    private final CompilationRepository repository;
    private final StatsService service;

    @Override
    public List<CompilationResponseDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = repository.findAll(pageRequest).toList();
        } else {
            compilations = repository.findAllByPinned(pinned, pageRequest);
        }

        List<Event> events = compilations.stream().map(Compilation::getEvents).flatMap(List::stream).collect(Collectors.toList());
        Map<Long, Long> eventViews = service.getListEventViews(events);

        return compilations
                .stream()
                .map(it -> CompilationMapper.toCompilationResponseDto(it, eventViews))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponseDto getCompilationById(Long compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(()
                -> new NoSuchElementException("Компилция с ID = " + compId + " не найдена."));
        Map<Long, Long> eventViews = service.getListEventViews(compilation.getEvents());
        return CompilationMapper.toCompilationResponseDto(compilation, eventViews);
    }
}
