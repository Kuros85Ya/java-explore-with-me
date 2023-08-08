package ru.practicum.unauthorized.service;

import ru.practicum.common.model.Event;

import java.util.List;
import java.util.Map;

public interface StatsService {
    Long getEventView(Event event);
    void saveNewEventView(String ip, String path);

    Map<Long, Long> getListEventViews(List<Event> events);
}
