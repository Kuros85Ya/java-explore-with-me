package ru.practicum.admin.mapper;

import ru.practicum.admin.dto.AdminLocationPatchRequestDto;
import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.common.model.Location;

import javax.validation.ValidationException;
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

    public static Location toLocationPatchRequest(Location location, AdminLocationPatchRequestDto requestDto) {
        Float latitude;
        Float longitude;
        if (requestDto.getLatitude() == null || requestDto.getLongitude() == null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } else {
            latitude = requestDto.getLatitude();
            longitude = requestDto.getLongitude();
        }

        String description;
        if (requestDto.getDescription() == null) {
            description = location.getDescription();
        } else {
            if (requestDto.getDescription().length() > 300 || requestDto.getDescription().length() < 2) {
                throw new ValidationException("Длина описания локации должна быть от 2 до 300 символов");
            }
            description = requestDto.getDescription();
        }

        return new Location(
                location.getId(),
                latitude,
                longitude,
                description,
                location.getEvent()
        );
    }
}
