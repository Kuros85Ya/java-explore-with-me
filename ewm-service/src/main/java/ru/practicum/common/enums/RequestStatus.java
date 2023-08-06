package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum RequestStatus {
    PENDING,
    CANCELED,
    CONFIRMED,
    APPROVED,
    REJECTED;

    public static class StringToEnumConverter implements Converter<String, RequestStatus> {
        @Override
        public RequestStatus convert(String source) {
            return RequestStatus.valueOf(source.toUpperCase());
        }
    }
}
