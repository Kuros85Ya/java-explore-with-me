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
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     Boolean onlyAvailable,
                                                     SortType sort,
                                                     PageRequest pageRequest) {

        List<Event> events;
        if (onlyAvailable) {
            events = repository.getEventsByParametersUnauthorizedAvailable(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        } else {
            events = repository.getEventsByParametersUnauthorizedAll(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        }
        Map<Long, Long> eventViews = service.getListEventViews(events);

        return events
                .stream()
                .map(it-> EventMapper.toEventResponseDto(it, eventViews.get(it.getId())))
                .collect(Collectors.toList());

        //todo avtuman1 сделай сортировку по частоте просмотров
    }



    @Override
    public CommonSingleEventResponse getEventById(Long id) {
        Event event = repository.getEventByIdAndStatus(id, EventState.PUBLISHED.name());
        if (event == null) throw new NoSuchElementException("Событие по id не найдено");
        Long views = service.saveNewEventView(event);
        return EventMapper.toEventResponseDto(event, views);
    }
}
