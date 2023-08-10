package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.admin.dto.AdminEventInitiator;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.model.Category;

@Data
@AllArgsConstructor
public class CommonSingleEventResponse {
    private String annotation;
    private Category category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private AdminEventInitiator initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;
}
