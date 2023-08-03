package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.admin.enums.StateAction;

@Data
public class AdminPatchEventRequestDto {
    String annotation;
    Long category;
    String eventDate; // 2022-09-06 11:00:23
    String description;
    AdminEventLocation location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    String title;
}
