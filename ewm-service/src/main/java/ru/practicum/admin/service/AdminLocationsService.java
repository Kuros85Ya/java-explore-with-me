package ru.practicum.admin.service;

import ru.practicum.admin.dto.AdminLocationPatchRequestDto;
import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.common.dto.CompilationResponseDto;

public interface AdminLocationsService {

    AdminLocationResponseDto addLocation(AdminLocationRequestDto requestDto);

    AdminLocationResponseDto patchLocation(Long locationId, AdminLocationPatchRequestDto requestDto);

    CompilationResponseDto createCompilationByLocationId(Long locationId, Long maxDistance, String compilationTitle, Boolean pinned);
}
