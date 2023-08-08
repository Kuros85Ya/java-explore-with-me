package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.common.model.Event;
import ru.practicum.stats.dto.StatsGetResponseDto;
import ru.practicum.stats.dto.StatsHitRequestDto;
import ru.practicum.unauthorized.mapper.StatsMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsClient client;

    @Override
    public Long getEventView(Event event) {
        List<StatsGetResponseDto> hits = client.getStats(StatsMapper.toStatsGetRequestDto(event, true));
        return getSingleEventViews(hits, event.getId());
    }

    @Override
    public void saveNewEventView(String ip, String path) {
        StatsHitRequestDto request = StatsMapper.toStatsHitRequestDto(ip, path);
        client.saveHit(request);
    }

    @Override
    public Map<Long, Long> getListEventViews(List<Event> events) {
        List<StatsGetResponseDto> hits = client.getStats(StatsMapper.toStatsGetRequestDtoList(events, true));
        Map<Long, Long> eventViews = new HashMap<>();
        events.forEach(it -> eventViews.put(it.getId(), getSingleEventViews(hits, it.getId())));
        return eventViews;
    }

    private Long getSingleEventViews(List<StatsGetResponseDto> hits, Long eventId) {
        Long views;
        Optional<StatsGetResponseDto> stat = hits
                .stream()
                .filter(it -> Objects.equals(it.getUri(), "/events/" + eventId))
                .findFirst();
        if (stat.isEmpty()) {
            views = 0L;
        } else {
            views = stat.get().getHits();
        }
        return views;
    }
}
