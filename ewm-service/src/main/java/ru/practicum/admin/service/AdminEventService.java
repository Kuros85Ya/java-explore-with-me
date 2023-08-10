package ru.practicum.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.common.dto.CommonSingleEventResponse;

import java.util.List;

public interface AdminEventService {

    List<CommonSingleEventResponse> getEvents(
            List<Long> users,
            List<String> states,
            List<Long> categories,
            String rangeStart,
            String rangeEnd,
            PageRequest pageRequest
    );

    CommonSingleEventResponse patchEvent(Long eventId, PatchEventRequestDto requestDto);
}
