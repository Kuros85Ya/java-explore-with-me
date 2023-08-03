package ru.practicum.admin.dto;

import lombok.Data;
import ru.practicum.admin.enums.State;

@Data
public class SingleAdminEventResponse {
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
