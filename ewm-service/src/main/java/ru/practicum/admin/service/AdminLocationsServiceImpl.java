package ru.practicum.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.dto.AdminLocationPatchRequestDto;
import ru.practicum.admin.dto.AdminLocationRequestDto;
import ru.practicum.admin.dto.AdminLocationResponseDto;
import ru.practicum.admin.mapper.CompilationMapper;
import ru.practicum.admin.mapper.LocationMapper;
import ru.practicum.common.dto.CompilationResponseDto;
import ru.practicum.common.model.Compilation;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Location;
import ru.practicum.common.repository.CompilationRepository;
import ru.practicum.common.repository.EventRepository;
import ru.practicum.common.repository.LocationRepository;
import ru.practicum.unauthorized.service.StatsService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLocationsServiceImpl implements AdminLocationsService {

    private final LocationRepository repository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final StatsService service;

    @Override
    public AdminLocationResponseDto addLocation(AdminLocationRequestDto requestDto) {
        Location location = repository.save(LocationMapper.toLocation(requestDto));
        return LocationMapper.toAdminLocationResponseDto(location);
    }

    @Override
    public AdminLocationResponseDto patchLocation(Long locationId, AdminLocationPatchRequestDto requestDto) {
        Location existingLocation = findLocationById(locationId);
        Location newLocation = LocationMapper.toLocationPatchRequest(existingLocation, requestDto);
        return LocationMapper.toAdminLocationResponseDto(repository.save(newLocation));
    }

    @Override
    public CompilationResponseDto createCompilationByLocationId(Long locationId, Long maxDistance, String compilationTitle, Boolean pinned) {
        Location existingLocation = findLocationById(locationId);
        List<Event> events = eventRepository.getEventsByLocationAll(existingLocation.getLatitude(), existingLocation.getLongitude(), maxDistance);

        String newTitle = setCompilationTitle(compilationTitle, existingLocation);
        Compilation compilation = compilationRepository.save(CompilationMapper.eventsToCompilation(events, newTitle, pinned));

        Map<Long, Long> eventViews = service.getListEventViews(events);
        return CompilationMapper.toCompilationResponseDto(compilation, eventViews);
    }

    private Location findLocationById(Long locationId) {
        return repository.findById(locationId).orElseThrow(()
                -> new NoSuchElementException("Локация с ID = " + locationId + " не найдена."));
    }

    private String setCompilationTitle(String compilationTitle, Location location) {
        String newTitle;
        newTitle = Objects.requireNonNullElseGet(compilationTitle, () -> ("Подборка событий для локации " + location.toString()));
        return newTitle;
    }
}
