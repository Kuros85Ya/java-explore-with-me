package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.enums.SortType;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.model.Event;
import ru.practicum.common.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.practicum.common.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedEventServiceImpl implements UnauthorizedEventService {

    private final EventRepository repository;
    private final StatsService service;

    @Override
    public List<CommonSingleEventResponse> getEvents(String text,
                                                     List<Long> categories,
                                                     Boolean paid,
                                                     String rangeStart,
                                                     String rangeEnd,
                                                     Boolean onlyAvailable,
                                                     SortType sort,
                                                     PageRequest pageRequest,
                                                     String ip,
                                                     String path) {

        LocalDateTime startDt = setDefaultDt(rangeStart, LocalDateTime.now());
        LocalDateTime endDt = setDefaultDt(rangeEnd, TIME_MAX);
        validateTimeRange(endDt, startDt);

        List<Event> events;
        if (onlyAvailable) {
            events = repository.getEventsByParametersUnauthorizedAvailable(text, categories, paid, startDt, endDt, pageRequest);
        } else {
            events = repository.getEventsByParametersUnauthorizedAll(text, categories, paid, startDt, endDt, pageRequest);
        }
        Map<Long, Long> eventViews = service.getListEventViews(events);
        service.saveNewEventView(ip, path);

        return events
                .stream()
                .map(it -> EventMapper.toEventResponseDto(it, eventViews.get(it.getId())))
                .collect(Collectors.toList());
    }


    @Override
    public CommonSingleEventResponse getEventById(Long id, String ip, String path) {
        Event event = repository.getEventByIdAndStatus(id, EventState.PUBLISHED.name());
        if (event == null) throw new NoSuchElementException("Событие по id не найдено");
        service.saveNewEventView(ip, path);
        Long views = service.getEventView(event);
        return EventMapper.toEventResponseDto(event, views);
    }
}
