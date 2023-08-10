package ru.practicum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester(User requester);

    List<Request> findAllByEvent(Event event);

    @Query("select count(*) from Request as r where r.event.id = :eventId and r.status = 'CONFIRMED'")
    Integer getCurrentNumberOfValidRequests(Long eventId);

    @Query("select r from Request as r where r.id in (:requestIds) order by r.created")
    List<Request> getAllRequestsFromListOfIds(List<Long> requestIds);
}
