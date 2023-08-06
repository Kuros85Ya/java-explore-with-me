package ru.practicum.unauthorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface UnauthorizedEventService {

    List<CommonSingleEventResponse> getEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            SortType sort,
            PageRequest pageRequest
    );

    CommonSingleEventResponse getEventById(Long id);
}
