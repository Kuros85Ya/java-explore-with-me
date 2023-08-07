package ru.practicum.admin.mapper;

import ru.practicum.admin.dto.AdminCompilationRequestDto;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.model.Compilation;
import ru.practicum.common.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(AdminCompilationRequestDto requestDto, List<Event> events) {
        return new Compilation(
                null,
                requestDto.getTitle(),
                requestDto.getPinned(),
                events
        );
    }

    public static CompilationResponseDto toCompilationResponseDto(Compilation compilation) {
        List<CommonSingleEventResponse> events = compilation.getEvents().stream().map(EventMapper::toEventResponseDto).collect(Collectors.toList());

        return new CompilationResponseDto(
                events,
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    public static Compilation patchCompilation(Compilation compilation, AdminCompilationRequestDto requestDto, List<Event> eventList) {
        List<Event> newEvents;
        if (requestDto.getEvents() == null) {
            newEvents = compilation.getEvents();
        } else {
            newEvents = eventList;
        }

        Boolean newPinned;
        if (requestDto.getPinned() == null) {
            newPinned = compilation.getPinned();
        } else {
            newPinned = requestDto.getPinned();
        }

        String newTitle;
        if (requestDto.getTitle() == null) {
            newTitle = compilation.getTitle();
        } else {
            newTitle = requestDto.getTitle();
        }

        return new Compilation(compilation.getId(),
                newTitle,
                newPinned,
                newEvents);
    }
}
