package ru.practicum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
