package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static class StringToEnumConverter implements Converter<String, EventState> {
        @Override
        public EventState convert(String source) {
            return EventState.valueOf(source.toUpperCase());
        }
    }
}
