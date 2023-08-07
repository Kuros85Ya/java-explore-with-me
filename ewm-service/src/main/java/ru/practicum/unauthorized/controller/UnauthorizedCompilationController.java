package ru.practicum.unauthorized.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.CompilationResponseDto;
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
    public List<CompilationResponseDto> getCompilations(
           @RequestParam(required = false) Boolean pinned,
           @RequestParam(defaultValue = "0") Integer from,
           @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получить компиляции событий pinned = {}, from = {}, size = {}", pinned, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getCompilations(pinned, pageRequest);
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getSingleCompilation(@PathVariable Long compId) {
        log.info("Получить компилцию по id = {}", compId);
        return service.getCompilationById(compId);
    }
}
