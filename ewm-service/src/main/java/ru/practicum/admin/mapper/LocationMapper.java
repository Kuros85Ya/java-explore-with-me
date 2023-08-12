package ru.practicum.admin.mapper;

import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.common.model.Location;

import java.util.Collections;

public class LocationMapper {

    public static Location toLocation(AdminLocationRequestDto requestDto) {
        return new Location(
                null,
                requestDto.getLatitude(),
                requestDto.getLongitude(),
                requestDto.getDescription(),
                null
        );
    }

    public static AdminLocationResponseDto toAdminLocationResponseDto(Location location) {
        return new AdminLocationResponseDto(
                location.getId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                Collections.emptyList()
        );
    }
}
