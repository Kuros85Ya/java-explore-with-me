package ru.practicum.unauthorized.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;

import java.util.List;

public interface UnauthorizedEventService {

    List<CommonSingleEventResponse> getEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            SortType sort,
            PageRequest pageRequest,
            String ip,
            String path);

    CommonSingleEventResponse getEventById(Long id, String ip, String path);
}
