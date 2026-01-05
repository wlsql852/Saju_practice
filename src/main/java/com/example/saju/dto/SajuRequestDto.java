package com.example.saju.dto;

public record SajuRequestDto(
        Pillar year,
        Pillar month,
        Pillar day,
        Pillar time
) {
}
