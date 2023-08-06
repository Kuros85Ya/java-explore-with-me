package ru.practicum.common.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.common.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event as e where " +
            "e.creator.id IN (:users) " +
            "AND e.status IN (:states) " +
            "AND e.category.id IN (:categories) " +
            "AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd ")
    List<Event> getEventsByParameters(List<Long> users,
                                      List<String> states,
                                      List<Long> categories,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      PageRequest pageRequest);
}
