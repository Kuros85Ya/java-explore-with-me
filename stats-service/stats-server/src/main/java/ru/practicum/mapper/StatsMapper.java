package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.model.Hit;
import ru.practicum.model.StatDb;
import ru.practicum.stats.dto.StatsGetRequestDto;
import ru.practicum.stats.dto.StatsGetResponseDto;
import ru.practicum.stats.dto.StatsHitRequestDto;
import ru.practicum.util.LocalDateTimeParser;

import java.util.List;

@Component
public class StatsMapper {

    public static Hit toHitDb(StatsHitRequestDto requestDto) {
        return new Hit(null,
                requestDto.getApp(),
                requestDto.getUri(),
                requestDto.getIp(),
                LocalDateTimeParser.parseDttm(requestDto.getTimestamp()));
    }

    public static StatsGetRequestDto toStatsRequestDto(String start, String end, Boolean unique, List<String> uris) {
        return new StatsGetRequestDto(start,
                end,
                unique,
                uris);
    }

    public static StatsGetResponseDto toStatsResponseDto(StatDb stat) {
        return new StatsGetResponseDto(stat.getApp(), stat.getUri(), stat.getCount());
    }
}
