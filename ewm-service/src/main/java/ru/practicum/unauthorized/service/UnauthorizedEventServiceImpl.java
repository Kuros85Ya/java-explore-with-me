package ru.practicum.unauthorized.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.SortType;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedEventServiceImpl implements UnauthorizedEventService {

    @Override
    public List<CommonSingleEventResponse> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, PageRequest pageRequest) {
        return null;
    }

    @Override
    public CommonSingleEventResponse getEventById(Long id) {
        return null;
    }
}
