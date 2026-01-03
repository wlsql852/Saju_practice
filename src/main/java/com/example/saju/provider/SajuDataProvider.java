package com.example.saju.provider;

import com.example.saju.dto.Pillar;

import java.time.LocalTime;

public interface SajuDataProvider {
    String minusPlusOf(String yearSky);
    Pillar getTimePillar(String daySky, LocalTime time);
}
