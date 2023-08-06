package ru.practicum.common.enums;

import org.springframework.core.convert.converter.Converter;

public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT,
    CANCEL_REVIEW;

    public static class StringToEnumConverter implements Converter<String, StateAction> {
        @Override
        public StateAction convert(String source) {
            return StateAction.valueOf(source.toUpperCase());
        }
    }
}
