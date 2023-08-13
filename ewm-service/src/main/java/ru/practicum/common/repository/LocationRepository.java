package ru.practicum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.common.model.AverageLocation;
import ru.practicum.common.model.Location;

import javax.validation.constraints.NotEmpty;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location getLocationByLatitudeAndLongitude(@NotEmpty Float latitude, @NotEmpty Float longitude);

    @Query("select new ru.practicum.common.model.AverageLocation(avg(l.longitude), avg(l.longitude)) " +
            "from Location as l " +
            "join Event as e on e.location.id = l.id " +
            "join Request r on r.event.id = e.id " +
            "where r.requester.id = :userId ")
    AverageLocation getFavoriteLocationByUserId(Long userId);
}
