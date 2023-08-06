package ru.practicum.common;

import org.springframework.data.domain.PageRequest;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class util {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseDttm(String dttm) {
        return LocalDateTime.parse(dttm, formatter);
    }

    public static String dttmToString(LocalDateTime dttm) {
        if (dttm == null) {
            return null;
        } else {
            return dttm.format(formatter);
        }
    }

    public static PageRequest toPageRequest(Integer from, Integer size) {
        if (from < 0 || size < 1) {
            throw new ValidationException("Недопустимое значение для параметров from и size");
        }

        return PageRequest.of(from / size, size);
    }
}
