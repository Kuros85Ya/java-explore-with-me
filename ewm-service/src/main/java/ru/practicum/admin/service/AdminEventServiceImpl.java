package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminPatchEventRequestDto;
import ru.practicum.admin.dto.SingleAdminEventResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    @Override
    public List<SingleAdminEventResponse> getEvents(List<Integer> users,
                                                    List<String> states,
                                                    List<Integer> categories,
                                                    String rangeStart,
                                                    String rangeEnd,
                                                    PageRequest pageRequest) {
        return null;
    }

    @Override
    public void patchEvent(Long eventId, AdminPatchEventRequestDto requestDto) {

    }
}
