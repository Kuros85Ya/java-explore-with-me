package ru.practicum.unauthorized.mapper;

import ru.practicum.common.model.Event;
import ru.practicum.stats.dto.StatsGetRequestDto;
import ru.practicum.stats.dto.StatsHitRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.common.util.dttmToString;

public class StatsMapper {
    private final static String APPLICATION = "ewm-service";
    private final static LocalDateTime TIME_MAX = LocalDateTime.of(3000, 12, 1, 10, 1);
    private final static LocalDateTime TIME_MIN = LocalDateTime.of(2000, 12, 1, 10, 1);

    public static StatsGetRequestDto toStatsGetRequestDto(Event event, Boolean unique) {
        String eventPath = "/events/" + event.getId();

        return new StatsGetRequestDto(dttmToString(TIME_MIN),
                dttmToString(TIME_MAX),
                unique,
                List.of(eventPath));
    }

    public static StatsGetRequestDto toStatsGetRequestDtoList(List<Event> events, Boolean unique) {
        List<String> uris = events.stream().map(it -> "/events/" + it.getId()).collect(Collectors.toList());

        return new StatsGetRequestDto(
                dttmToString(TIME_MIN),
                dttmToString(TIME_MAX),
                unique,
                uris
        );
    }

    public static StatsHitRequestDto toStatsHitRequestDto(String ip, String path) {
        return new StatsHitRequestDto(APPLICATION,
                path,
                ip,
                dttmToString(LocalDateTime.now()));
    }
}
