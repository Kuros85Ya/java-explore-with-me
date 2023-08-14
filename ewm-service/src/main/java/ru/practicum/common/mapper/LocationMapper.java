package ru.practicum.common.mapper;

import ru.practicum.common.model.Location;

public class LocationMapper {

    public static Location toLocation(ru.practicum.common.dto.Location location) {
        return new Location(null,
                location.getLat(),
                location.getLon(),
                null,
                null);
    }
}
