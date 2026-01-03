package com.example.saju.dto;

public record SaJuResponseDto(
        Pillar year,
        Pillar month,
        Pillar day,
        Pillar time,
        Integer bigFortuneNumber,
        Integer bigFortuneStartYear
) {
}
