package ru.practicum.authorized.dto;

import lombok.Data;
import ru.practicum.common.dto.Location;

import javax.validation.constraints.*;

@Data
public class AuthorizedEventRequestDto {
    @NotBlank
    @Size(min = 20)
    @Size(max = 2000)
    private String annotation;
    private Long category;
    private String eventDate;
    @NotBlank
    @Size(min = 20)
    @Size(max = 7000)
    private String description;
    @NotNull
    private Location location;
    private Boolean paid = false;
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
    @Size(min = 3)
    @Size(max = 120)
    private String title;
}
