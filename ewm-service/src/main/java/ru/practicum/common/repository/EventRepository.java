package ru.practicum.common.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.User;

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

    List<Event> getEventsByCreatorIs(User creator, PageRequest pageRequest);

    @Query("select e from Event as e " +
            "where (upper(e.description) like upper('%' || :text || '%') " +
            "or upper(e.annotation) like upper('%' || :text || '%') ) " +
            "and e.category.id in (:categories) " +
            "and e.paid = :paid " +
            "and e.status = 'PUBLISHED' " +
            "and e.eventDate > :rangeStart " +
            "and e.eventDate < :rangeEnd " +
            "and e.participantLimit >" +
            "      (select count(*) " +
            "       from Request as r" +
            "       where r.event.id = e.id " +
            "           and r.status = 'APPROVED') " +
            "order by e.eventDate")
    List<Event> getEventsByParametersUnauthorizedAvailable(String text,
                                                           List<Long> categories,
                                                           Boolean paid,
                                                           LocalDateTime rangeStart,
                                                           LocalDateTime rangeEnd,
                                                           PageRequest pageRequest
    );

    @Query("select e from Event as e " +
            "where (upper(e.description) like upper( '%' || :text || '%')) " +
            "or upper(e.annotation) like upper( '%' || :text || '%') " +
            "and e.category.id in (:categories) " +
            "and e.paid = :paid " +
            "and e.status = 'PUBLISHED' " +
            "and e.eventDate > :rangeStart " +
            "and e.eventDate < :rangeEnd " +
            "order by e.eventDate")
    List<Event> getEventsByParametersUnauthorizedAll(String text,
                                                     List<Long> categories,
                                                     Boolean paid,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     PageRequest pageRequest
    );

    Event getEventByIdAndStatus(Long id, String status);

    Event getEventByIdAndCreator(Long id, User creator);

}
