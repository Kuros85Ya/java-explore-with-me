package ru.practicum.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class util {
    public static LocalDateTime parseDttm(String dttm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dttm, formatter);
    }

}
