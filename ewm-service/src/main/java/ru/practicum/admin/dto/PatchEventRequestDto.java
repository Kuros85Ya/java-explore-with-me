package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;
import ru.practicum.common.enums.StateAction;

import javax.validation.constraints.Size;

@Data
public class PatchEventRequestDto {
    @Size(min = 20)
    @Size(max = 2000)
    String annotation;
    Long category;
    String eventDate;
    @Size(min = 20)
    @Size(max = 7000)
    String description;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    @Size(min = 3)
    @Size(max = 120)
    String title;
}
