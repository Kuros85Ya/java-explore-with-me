package ru.practicum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.common.model.Event;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminUserLocationResponseDto {
    private Long id;
    private Float latitude;
    private Float longitude;
    private String description;
    private List<Event> event;
}
