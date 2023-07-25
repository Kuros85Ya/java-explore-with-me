package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.StatsGetRequestDto;
import ru.practicum.dto.StatsHitRequestDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    public static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private final StatsClient client;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveHit(@RequestBody @Valid StatsHitRequestDto requestDto) {
        log.info("Saving hit={}", requestDto);
        return client.saveHit(requestDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
//        log.info("Get all items of user={}, page from={}, size={}", userId, from, size);
//        return client.getStats(from, size);
        return client.getStats(new StatsGetRequestDto());
    }
}
