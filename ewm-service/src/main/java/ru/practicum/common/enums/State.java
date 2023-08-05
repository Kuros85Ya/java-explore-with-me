package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum State {
    PUBLISHED;

    public static class StringToEnumConverter implements Converter<String, State> {
        @Override
        public State convert(String source) {
            return State.valueOf(source.toUpperCase());
        }
    }
}
