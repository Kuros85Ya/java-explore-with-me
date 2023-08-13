package ru.practicum.common.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getAllByIdIn(List<Long> ids);

    @Query("select e from Event as e where " +
            "(coalesce(:users, null) is null OR e.creator.id in (:users)) " +
            "and (coalesce(:states, null) is null OR e.status in (:states)) " +
            "and (coalesce(:categories, null) is null OR e.category.id in (:categories)) " +
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
            "where (upper(e.description) like upper('%' || :text || '%')  or upper(e.annotation) like upper('%' || :text || '%')) " +
            "and (:categories is null OR e.category.id in (:categories)) " +
            "and (:paid is null OR e.paid = :paid) " +
            "and e.status = 'PUBLISHED' " +
            "and e.eventDate > :rangeStart " +
            "and e.eventDate < :rangeEnd " +
            "and ((:latitude is null OR :longitude is null OR :maxDistance is null ) " +
            "OR (distance(e.location.latitude, e.location.longitude, :latitude, :longitude) < :maxDistance)) " +
            "and e.participantLimit >" +
            "      (select count(*) " +
            "       from Request as r" +
            "       where r.event.id = e.id " +
            "           and r.status = 'CONFIRMED') " +
            "order by e.eventDate")
    List<Event> getEventsByParametersUnauthorizedAvailable(String text,
                                                           List<Long> categories,
                                                           Boolean paid,
                                                           LocalDateTime rangeStart,
                                                           LocalDateTime rangeEnd,
                                                           Float latitude,
                                                           Float longitude,
                                                           Long maxDistance,
                                                           PageRequest pageRequest
    );

    @Query("select e from Event as e " +
            "where ((upper(e.description) like upper( '%' || :text || '%')) " +
            "or upper(e.annotation) like upper( '%' || :text || '%')) " +
            "and (:categories is null OR e.category.id in (:categories)) " +
            "and (:paid is null OR e.paid = :paid) " +
            "and e.status = 'PUBLISHED' " +
            "and e.eventDate > :rangeStart " +
            "and e.eventDate < :rangeEnd " +
            "and ((:latitude is null OR :longitude is null OR :maxDistance is null) " +
            "OR (distance(e.location.latitude, e.location.longitude, :latitude, :longitude) < :maxDistance)) " +
            "order by e.eventDate")
    List<Event> getEventsByParametersUnauthorizedAll(String text,
                                                     List<Long> categories,
                                                     Boolean paid,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     Float latitude,
                                                     Float longitude,
                                                     Long maxDistance,
                                                     PageRequest pageRequest
    );

    @Query("select e from Event as e " +
            "where e.status = 'PUBLISHED' " +
            "and e.eventDate > now() " +
            "and (:latitude is null OR :longitude is null OR :maxDistance is null) " +
            "OR (distance(e.location.latitude, e.location.longitude, :latitude, :longitude) < :maxDistance) " +
            "order by e.eventDate")
    List<Event> getEventsByLocationPageable(Float latitude, Float longitude, Long maxDistance, PageRequest pageRequest);

    @Query("select e from Event as e " +
            "where e.status = 'PUBLISHED' " +
            "and e.eventDate > now() " +
            "and (:latitude is null OR :longitude is null OR :maxDistance is null) " +
            "OR (distance(e.location.latitude, e.location.longitude, :latitude, :longitude) < :maxDistance) " +
            "order by e.eventDate")
    List<Event> getEventsByLocationAll(Float latitude, Float longitude, Long maxDistance);

    Event getEventByIdAndStatus(Long id, String status);

    Event getEventByIdAndCreator(Long id, User creator);

    @Query("select e from Event as e " +
            "join Request r on r.event.id = r.event.id " +
            "where r.requester.id = :userId " +
            "order by r.created desc ")
    List<Event> getVisitedEventsByUserId(Long userId);

}
