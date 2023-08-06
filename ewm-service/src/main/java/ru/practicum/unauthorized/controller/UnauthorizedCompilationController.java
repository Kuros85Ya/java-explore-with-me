package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.unauthorized.dto.UnauthorizedCompilationResponseDto;
import ru.practicum.unauthorized.service.UnauthorizedCompilationService;

import java.util.List;

import static ru.practicum.common.util.toPageRequest;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnauthorizedCompilationController {

    private final UnauthorizedCompilationService service;

    @GetMapping()
    public List<UnauthorizedCompilationResponseDto> getCompilations(
           @RequestParam Boolean pinned,
           @RequestParam Integer from,
           @RequestParam Integer size) {
        log.info("Получить компиляции событий pinned = {}, from = {}, size = {}", pinned, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getCompilations(pinned, pageRequest);
    }

    @GetMapping("/{compId}")
    public UnauthorizedCompilationResponseDto getSingleCompilation(@PathVariable Long compId) {
        log.info("Удаление администратором компиляции {}", compId);
        return service.getCompilationById(compId);
    }
}
