package ru.practicum.unauthorized.service;

import ru.practicum.common.model.Event;

import java.util.List;
import java.util.Map;

public interface StatsService {
    Long getEventView(Event event);
    Long saveNewEventView(Event event);

    Map<Long, Long> getListEventViews(List<Event> events);
}
