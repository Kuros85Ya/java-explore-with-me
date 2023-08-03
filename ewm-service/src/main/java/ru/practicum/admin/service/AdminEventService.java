package ru.practicum.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.admin.dto.SingleAdminEventResponse;

import java.util.List;

public interface AdminEventService {

    /**Добавление категории событий. Имя категории должно быть уникальным**/
    List<SingleAdminEventResponse> getEvents(
            List<Integer> users,
            List<String> states,
            List<Integer> categories,
            String rangeStart,
            String rangeEnd,
            PageRequest pageRequest
    );

    void patchEvent(Long eventId, AdminPatchEventRequestDto requestDto);
}
