package ru.practicum.authorized.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizedEventRequestDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
