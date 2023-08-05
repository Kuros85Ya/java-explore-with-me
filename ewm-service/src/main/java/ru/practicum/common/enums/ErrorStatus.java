package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum ErrorStatus {
    BAD_REQUEST,
    NOT_FOUND,
    CONFLICT;

    public static class StringToEnumConverter implements Converter<String, ErrorStatus> {
        @Override
        public ErrorStatus convert(String source) {
            return ErrorStatus.valueOf(source.toUpperCase());
        }
    }
}
