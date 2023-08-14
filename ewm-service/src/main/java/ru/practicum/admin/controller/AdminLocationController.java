package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.AdminLocationPatchRequestDto;
import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.admin.service.AdminLocationsService;
import ru.practicum.common.dto.CompilationResponseDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/locations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminLocationController {

    private final AdminLocationsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdminLocationResponseDto createLocation(@RequestBody @Valid AdminLocationRequestDto requestDto) {
        log.info("Добавление локации администратором {}", requestDto);
        return service.addLocation(requestDto);
    }

    @PatchMapping("/{locationId}")
    public AdminLocationResponseDto patchExistingLocation(@PathVariable Long locationId, @RequestBody AdminLocationPatchRequestDto requestDto) {
        log.info("Изменение существующей локации id = {} измененное описание = {}", locationId, requestDto);
        return service.patchLocation(locationId, requestDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{locationId}/compilation")
    public CompilationResponseDto createCompilationByLocationId(@PathVariable Long locationId, @RequestParam(defaultValue = "1000") Long maxDistance, @RequestParam(required = false) String compilationDescription, @RequestParam(defaultValue = "false") Boolean pinned) {
        log.info("Создание компиляции из событий находящихся близко с выбранной точкой");
        return service.createCompilationByLocationId(locationId, maxDistance, compilationDescription, pinned);
    }
}
