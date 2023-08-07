package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.common.model.Event;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    @Override
    public Long getEventView(Event event) {
        return null;
    }

    @Override
    public Long saveNewEventView(Event event) {
        return null;
    }

    @Override
    public Map<Long, Long> getListEventViews(List<Event> events) {
        return null;
    }
}
