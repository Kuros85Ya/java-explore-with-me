package ru.practicum.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

public interface AdminEventService {

    /**Добавление категории событий. Имя категории должно быть уникальным**/
    List<CommonSingleEventResponse> getEvents(
            List<Long> users,
            List<String> states,
            List<Long> categories,
            String rangeStart,
            String rangeEnd,
            PageRequest pageRequest
    );

    CommonSingleEventResponse patchEvent(Long eventId, AdminPatchEventRequestDto requestDto);
}
