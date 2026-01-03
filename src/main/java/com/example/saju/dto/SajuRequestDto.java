package com.example.saju.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SajuRequestDto(String birthType,
                             LocalDate birthDay,
                             LocalTime time,
                             String gender
) {
}
