package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;
import ru.practicum.common.enums.StateAction;

@Data
public class AdminPatchEventRequestDto {
    String annotation;
    Long category;
    String eventDate; // 2022-09-06 11:00:23
    String description;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    String title;
}
