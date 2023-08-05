package ru.practicum.common.dto;

import lombok.Data;
import ru.practicum.admin.dto.AdminCategory;
import ru.practicum.admin.dto.AdminEventInitiator;
import ru.practicum.admin.dto.AdminEventLocation;
import ru.practicum.common.enums.State;

@Data
public class CommonSingleEventResponse {
    String annotation;
    AdminCategory category;
    Long confirmedRequests;
    String createdOn; // 2022-09-06 11:00:23
    String description;
    String eventDate; // 2024-12-31 15:10:05
    Long id;
    AdminEventInitiator initiator;
    AdminEventLocation location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn; //"2022-09-06 15:10:05"
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}
