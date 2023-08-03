package ru.practicum.admin.mapper;

import org.springframework.data.domain.PageRequest;

import javax.validation.ValidationException;

public class RequestMapper {
    public static PageRequest toPageRequest(Integer from, Integer size) {
        if (from < 0 || size < 1) {
            throw new ValidationException("Недопустимое значение для параметров from и size");
        }

        return PageRequest.of(from / size, size);
    }
}
