package com.example.saju.service;

import com.example.saju.common.FiveElementGround;
import com.example.saju.common.FiveElementSky;
import com.example.saju.dto.FiveElementSimpleResponseDto;
import com.example.saju.dto.SajuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiveElementService {
    private final int YEAR_SKY_WEIGHT = 4;
    private final int YEAR_EARTH_WEIGHT = 13;
    private final int MONTH_SKY_WEIGHT = 9;
    private final int MONTH_EARTH_WEIGHT = 30;
    private final int DAY_EARTH_WEIGHT = 20;
    private final int TIME_SKY_WEIGHT = 9;
    private final int TIME_EARTH_WEIGHT = 15;


    public FiveElementSimpleResponseDto getFiveElement(SajuRequestDto requestDto) {
        int[] score = new int [5]; //목,화,토,금,수
        int index = FiveElementSky.getElementidxBySky(requestDto.year().sky());
        score[index] += YEAR_SKY_WEIGHT;
        index = FiveElementGround.getElementidxByGround(requestDto.year().ground());
        score[index] += YEAR_EARTH_WEIGHT;
        index = FiveElementSky.getElementidxBySky(requestDto.month().sky());
        score[index] += MONTH_SKY_WEIGHT;
        index = FiveElementGround.getElementidxByGround(requestDto.month().ground());
        score[index] += MONTH_EARTH_WEIGHT;
        index = FiveElementGround.getElementidxByGround(requestDto.day().ground());
        score[index] += DAY_EARTH_WEIGHT;
        index = FiveElementSky.getElementidxBySky(requestDto.time().sky());
        score[index] += TIME_SKY_WEIGHT;
        index = FiveElementGround.getElementidxByGround(requestDto.time().ground());
        score[index] += TIME_EARTH_WEIGHT;
        return new FiveElementSimpleResponseDto(score[0], score[1], score[2], score[3], score[4]);
    }
}
