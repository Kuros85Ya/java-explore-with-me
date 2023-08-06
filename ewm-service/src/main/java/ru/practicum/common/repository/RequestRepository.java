package ru.practicum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.model.Event;
import ru.practicum.common.model.Request;
import ru.practicum.common.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester(User requester);

    List<Request> findAllByEvent(Event event);
}
