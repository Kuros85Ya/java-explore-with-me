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

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.util.LocalDateTimeParser.parseDttm;

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
        LocalDateTime startDt = parseDttm(requestDto.getStartDttm());
        LocalDateTime endDt = parseDttm(requestDto.getEndDttm());

        if (endDt.isBefore(startDt)) {
            throw new ValidationException("Дата начала диапазона не может быть больше даты окончания");
        }

        if (requestDto.getUris() != null && !requestDto.getUris().isEmpty()) {
            if (requestDto.getUnique()) {
                stats = repository.getHitsByAppAndUrisUnique(startDt, endDt, requestDto.getUris());
            } else {
                stats = repository.getHitsByAppAndUris(startDt, endDt, requestDto.getUris());
            }
        } else {
            if (requestDto.getUnique()) {
                stats = repository.getHitsUnique(startDt, endDt);
            } else {
                stats = repository.getHits(startDt, endDt);
            }
        }
        return stats.stream().map(StatsMapper::toStatsResponseDto).collect(Collectors.toList());

    }
}
