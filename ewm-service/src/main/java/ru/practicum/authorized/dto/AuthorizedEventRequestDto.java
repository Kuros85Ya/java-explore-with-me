package ru.practicum.authorized.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;

@Data
public class AuthorizedEventRequestDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
}
