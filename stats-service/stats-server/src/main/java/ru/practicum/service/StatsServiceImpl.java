package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Hit;
import ru.practicum.model.StatDb;
import ru.practicum.repository.StatsRepository;
import ru.practicum.stats.dto.StatsGetRequestDto;
import ru.practicum.stats.dto.StatsGetResponseDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public void saveHit(Hit hit) {
        repository.save(hit);
    }

    @Override
    public List<StatsGetResponseDto> getStats(StatsGetRequestDto requestDto) {
        List<StatDb> stats;

        if (requestDto.getUris() != null && !requestDto.getUris().isEmpty()) {
            if (requestDto.getUnique()) {
                stats = repository.getHitsByAppAndUrisUnique(requestDto.getStartDttm(), requestDto.getEndDttm(), requestDto.getUris());
            } else {
                stats = repository.getHitsByAppAndUris(requestDto.getStartDttm(), requestDto.getEndDttm(), requestDto.getUris());
            }
        } else {
            if (requestDto.getUnique()) {
                stats = repository.getHitsUnique(requestDto.getStartDttm(), requestDto.getEndDttm());
            } else {
                stats = repository.getHits(requestDto.getStartDttm(), requestDto.getEndDttm());
            }
        }
        return stats.stream().map(StatsMapper::toStatsResponseDto).collect(Collectors.toList());

    }
}
