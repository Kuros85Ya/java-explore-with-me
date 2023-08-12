package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminLocationPatchRequestDto;
import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.admin.dto.AdminUserLocationResponseDto;
import ru.practicum.admin.mapper.LocationMapper;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.common.model.Location;
import ru.practicum.common.repository.LocationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLocationsServiceImpl implements AdminLocationsService {

    private final LocationRepository repository;
    @Override
    public AdminLocationResponseDto addLocation(AdminLocationRequestDto requestDto) {
        Location location = repository.save(LocationMapper.toLocation(requestDto));
        return LocationMapper.toAdminLocationResponseDto(location);
    }

    @Override
    public AdminLocationResponseDto patchLocation(Long locationId, AdminLocationPatchRequestDto requestDto) {
        return null;
    }

    @Override
    public AdminUserLocationResponseDto addLocationToUser(Long locationId, Long userId, Boolean isFavorite, Boolean isLastVisited) {
        return null;
    }

    @Override
    public CompilationResponseDto createCompilationByLocationId(Long locationId) {
        return null;
    }
}
