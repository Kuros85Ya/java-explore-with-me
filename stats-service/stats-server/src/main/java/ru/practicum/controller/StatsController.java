package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.service.StatsService;
import ru.practicum.stats.dto.StatsGetResponseDto;
import ru.practicum.stats.dto.StatsHitRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveHit(@RequestBody @Valid StatsHitRequestDto requestDto) {
        log.info("Saving hit={}", requestDto);
        service.saveHit(StatsMapper.toHitDb(requestDto));
    }

    @GetMapping("/stats")
    public List<StatsGetResponseDto> getStats(@RequestParam String start,
                                              @RequestParam String end,
                                              @RequestParam(defaultValue = "false") Boolean unique,
                                              @RequestParam(required = false) List<String> uris) {
        log.info("Get stats for period from={}, to={}, count unique ips={}", start, end, unique);
        return service.getStats(StatsMapper.toStatsRequestDto(start, end, unique, uris));
    }
}
