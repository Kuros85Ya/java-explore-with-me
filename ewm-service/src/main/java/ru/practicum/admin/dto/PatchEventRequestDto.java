package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;
import ru.practicum.common.enums.StateAction;

import javax.validation.constraints.Size;

@Data
public class PatchEventRequestDto {
    @Size(min = 20)
    @Size(max = 2000)
    private String annotation;
    private Long category;
    private String eventDate;
    @Size(min = 20)
    @Size(max = 7000)
    private String description;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3)
    @Size(max = 120)
    private String title;
}
