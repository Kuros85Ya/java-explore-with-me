package ru.practicum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.admin.dto.AdminEventInitiator;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.model.Category;

@Data
@AllArgsConstructor
public class CommonSingleEventResponse {
    String annotation;
    Category category;
    Long confirmedRequests;
    String createdOn; // 2022-09-06 11:00:23
    String description;
    String eventDate; // 2024-12-31 15:10:05
    Long id;
    AdminEventInitiator initiator;
    Location location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn; //"2022-09-06 15:10:05"
    Boolean requestModeration;
    EventState state;
    String title;
    Long views;
}
