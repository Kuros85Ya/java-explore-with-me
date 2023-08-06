package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.*;
import ru.practicum.admin.service.AdminCompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCompilationController {

    private final AdminCompilationService service;

    @PostMapping()
    public AdminCompilationResponseDto addCompilation(
            @RequestBody @Valid AdminCompilationRequestDto requestDto) {
        log.info("Добавление компиляции событий {}", requestDto);
        return service.addCompilation(requestDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Удаление администратором компиляции {}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public AdminCompilationResponseDto patchCompilation(@PathVariable Long compId,
                           @RequestBody @Valid AdminCompilationRequestDto requestDto) {
        return service.patchCompilation(compId, requestDto);
    }
}
