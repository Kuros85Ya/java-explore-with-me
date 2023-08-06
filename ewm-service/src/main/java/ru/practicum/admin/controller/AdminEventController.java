package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.admin.service.AdminEventService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.common.util.toPageRequest;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminEventController {

    private final AdminEventService service;

    @GetMapping()
    public List<CommonSingleEventResponse> getEvents(
            @RequestParam List<Long> users,
            @RequestParam List<String> states,
            @RequestParam List<Long> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Поиск событий по параметрам {} {} {} {} {} {} {}", users, states, categories,
                rangeStart, rangeEnd, from, size);
        PageRequest pageRequest = toPageRequest(from, size);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, pageRequest);
    }

    @PatchMapping("/{eventId}")
    public CommonSingleEventResponse patchEvent(@PathVariable Long eventId,
                           @RequestBody @Valid AdminPatchEventRequestDto requestDto) {
        log.info("Изменение администратором события {}, новое значение {}", eventId, requestDto);
        return service.patchEvent(eventId, requestDto);
    }
}
