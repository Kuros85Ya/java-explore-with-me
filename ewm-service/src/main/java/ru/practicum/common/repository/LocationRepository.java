package ru.practicum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.model.Location;

import javax.validation.constraints.NotEmpty;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location getLocationByLatitudeAndLongitude(@NotEmpty Float latitude, @NotEmpty Float longitude);
}
