package com.example.saju.common;

import com.example.saju.exception.SajuException;
import com.example.saju.exception.SajuErrorCode;

public enum GenderType {
    MALE, FEMALE;

    public static GenderType of(String value) {
        for (GenderType gender : GenderType.values()) {
            if (gender.name().equalsIgnoreCase(value)) return gender;
        }
        throw new SajuException(SajuErrorCode.INVALID_GENDERTYPE);
    }
}
