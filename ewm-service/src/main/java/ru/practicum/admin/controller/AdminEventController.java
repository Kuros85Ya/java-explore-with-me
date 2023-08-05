package ru.practicum.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.mapper.RequestMapper;
import ru.practicum.admin.service.AdminEventService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminEventController {

    private final AdminEventService service;

    @GetMapping()
    public List<CommonSingleEventResponse> getEvents(
            @RequestParam List<Integer> users,
            @RequestParam List<String> states,
            @RequestParam List<Integer> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam Integer from,
            @RequestParam Integer size) {
        log.info("Поиск событий по параметрам {} {} {} {} {} {} {}", users, states, categories,
                rangeStart, rangeEnd, from, size);
        PageRequest pageRequest = RequestMapper.toPageRequest(from, size);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, pageRequest);
    }

    @PatchMapping("/{eventId}")
    public void patchEvent(@PathVariable Long eventId,
                           @RequestBody @Valid AdminPatchEventRequestDto requestDto) {
        log.info("Изменение администратором события {}, новое значение {}", eventId, requestDto);
        service.patchEvent(eventId, requestDto);
    }
}
