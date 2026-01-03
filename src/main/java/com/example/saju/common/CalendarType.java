package com.example.saju.common;

import com.example.saju.exception.SajuErrorCode;
import com.example.saju.exception.SajuException;

public enum CalendarType {
    SOLAR, LUNAR;

    public static CalendarType of(String value) {
        for (CalendarType type : CalendarType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new SajuException(SajuErrorCode.INVALID_CALENDARTYPE);
    }
}
