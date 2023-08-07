package ru.practicum.authorized.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;

import javax.validation.constraints.*;

@Data
public class AuthorizedEventRequestDto {
    @NotBlank
    @Size(min = 20)
    @Size(max = 2000)
    String annotation;
    Long category;
    String eventDate;
    @NotBlank
    @Size(min = 20)
    @Size(max = 7000)
    String description;
    @NotNull
    Location location;
    Boolean paid = false;
    Integer participantLimit = 0;
    Boolean requestModeration = true;
    @Size(min = 3)
    @Size(max = 120)
    String title;
}
