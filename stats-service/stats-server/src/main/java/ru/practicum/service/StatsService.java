package ru.practicum.service;

import ru.practicum.model.Hit;
import ru.practicum.stats.dto.StatsGetRequestDto;
import ru.practicum.stats.dto.StatsGetResponseDto;

import java.util.List;

public interface StatsService {

    void saveHit(Hit hit);

    List<StatsGetResponseDto> getStats(StatsGetRequestDto requestDto);
}
