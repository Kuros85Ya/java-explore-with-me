package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum SortType {
    EVENT_DATE, VIEWS;

    public static class StringToEnumConverter implements Converter<String, SortType> {
        @Override
        public SortType convert(String source) {
            return SortType.valueOf(source.toUpperCase());
        }
    }
}
