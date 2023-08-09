package ru.practicum.common;

import org.springframework.data.domain.PageRequest;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static final String APPLICATION = "ewm-service";
    public static final LocalDateTime TIME_MAX = LocalDateTime.of(3000, 12, 1, 10, 1);
    public static final LocalDateTime TIME_MIN = LocalDateTime.of(2000, 12, 1, 10, 1);
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

    public static LocalDateTime setDefaultDt(String dttm, LocalDateTime defaultDt) {
        if (dttm == null) {
            return defaultDt;
        } else {
            return parseDttm(dttm);
        }
    }

    public static void validateTimeRange(LocalDateTime startDt, LocalDateTime endDt) {
        if (endDt.isBefore(startDt)) {
            throw new ValidationException("Конец диапазона не может быть раньше начала");
        }
    }
}
