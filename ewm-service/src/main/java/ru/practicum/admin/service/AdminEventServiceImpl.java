package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.PatchEventRequestDto;
import ru.practicum.authorized.service.AuthorizedEventServiceImpl;
import ru.practicum.common.dto.CommonSingleEventResponse;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.enums.StateAction;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.model.Category;
import ru.practicum.common.model.Event;
import ru.practicum.common.repository.EventRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.common.util.parseDttm;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    private final AuthorizedEventServiceImpl service;
    private final EventRepository repository;

    @Override
    public List<CommonSingleEventResponse> getEvents(List<Long> users,
                                                     List<String> states,
                                                     List<Long> categories,
                                                     String rangeStart,
                                                     String rangeEnd,
                                                     PageRequest pageRequest) {
        List<Event> events = repository.getEventsByParameters(
                users,
                states,
                categories,
                parseDttm(rangeStart),
                parseDttm(rangeEnd),
                pageRequest);



        return events
                .stream()
                .map(EventMapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommonSingleEventResponse patchEvent(Long eventId, PatchEventRequestDto requestDto) {
        Event event = service.findEventById(eventId);

        Long categoryId;
        if (requestDto.getCategory() == null) {
            categoryId = event.getCategory().getId();
        } else {
            categoryId = requestDto.getCategory();
        }

        Category category = service.findCategoryById(categoryId);

        validateEvent(event, requestDto.getStateAction());
        Event modifiedEvent = EventMapper.patchRequestToEvent(event, category, requestDto);
        repository.save(modifiedEvent);
        return EventMapper.toEventResponseDto(modifiedEvent);
    }

    /**
     * Дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
     * событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
     * событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
     * **/
    private void validateEvent(Event event, StateAction stateAction) {
        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(1))
                || ((stateAction == StateAction.PUBLISH_EVENT
                || stateAction == StateAction.REJECT_EVENT)
                && !Objects.equals(event.getStatus(), EventState.PENDING.name()))) {
            throw new ValidationException("Попытка выполнить недопустимое действие с событием");
        }
    }
}
