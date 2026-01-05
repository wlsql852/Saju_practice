package com.example.saju.service;

import com.example.saju.common.CalendarType;
import com.example.saju.common.GenderType;
import com.example.saju.dto.BirthRequestDto;
import com.example.saju.dto.Pillar;
import com.example.saju.dto.SaJuResponseDto;
import com.example.saju.entity.Manse;
import com.example.saju.exception.SajuErrorCode;
import com.example.saju.exception.SajuException;
import com.example.saju.provider.SajuDataProvider;
import com.example.saju.repository.ManseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SajuService {
    private final ManseRepository manseRepository;
    private final SajuDataProvider sajuDataProvider;

    /**
     * 생년월일시를 사주로 변환(메인로직)
     */
    public SaJuResponseDto convertBirthToSaju(BirthRequestDto requestDto) {
        CalendarType birthDayType = CalendarType.of(requestDto.birthType());
        GenderType gender = GenderType.of(requestDto.gender());
        LocalTime time  = requestDto.time() == null? LocalTime.of(12,0) : requestDto.time();  //시간 미입력시 12:00
        //1. 생년월일을 삼주(양력)로 변환
        Manse samju = convertBirthToSamju(birthDayType, requestDto.birthDay(), time);

        //2. 생년월일시(양력) 생성
        LocalDateTime solarDateTime = LocalDateTime.of(samju.getSolarDate(), time);

        //3. 순행(true), 역행(false) 판단
        boolean direction = isRightDirection(gender, samju.getYearSky());

        //4. 절입시간 가져오기
        LocalDateTime seasonTime = getSeasonStartTime(direction, solarDateTime);

        //5. 대운수 및 대운 시작년 가져오기
        BigFortuneResult bigFortune = getBigFortuneNumber(direction, seasonTime, solarDateTime);

        //6. 시주 가져오기
        Pillar timePillar = sajuDataProvider.getTimePillar(samju.getDaySky(), time);

        return new SaJuResponseDto(
                new Pillar(samju.getYearSky(), samju.getYearGround()),
                new Pillar(samju.getMonthSky(), samju.getMonthGround()),
                new Pillar(samju.getDaySky(), samju.getDayGround()),
                timePillar,
                bigFortune.bigFortuneNumber,
                bigFortune.bigFortuneStartYear);
    }

    //생년월일을 삼주로 변환
    private Manse convertBirthToSamju(CalendarType birthDayType, LocalDate birthDay, LocalTime time) {
        //23:30 ~ 23:59 자시에 태어난 경우 다음날로 처리
        //1987.02.13 00:30분  (계사일주 - 임자시)
        //1987.02.13 23:40분  (갑오일주 - 갑자시)
        if(!time.isBefore(LocalTime.of(23,30)) && !time.isAfter(LocalTime.of(12,0))) {
            birthDay = birthDay.plusDays(1);
        }
        Manse samju;
        if(birthDayType.equals(CalendarType.SOLAR)) samju = manseRepository.findBySolarDate(birthDay);
        else samju = manseRepository.findByLunarDate(birthDay);

        //절입일인 경우
        if(samju.getSeason() != null) {
            //절입시간과 생년연월시 비교
            //1987-02-04 17:47:00 이후 입춘 - 정묘년 임인월 갑신일, 이전 병인년 신축월 계미일
            //절입일이 생일보다 큰 경우는 하루 전 만세력을 가져와야 한다.
            LocalDateTime seasonStartTime = samju.getSeasonStartTime();
            LocalDateTime birth = LocalDateTime.of(birthDay, time);
            long diff = Duration.between(seasonStartTime, birth).toHours();
            if(diff < 0) samju = manseRepository.findBySolarDate(birthDay.minusDays(1));
        }
        return samju;
    }

    //순행(true), 역행(false) 판단 (성별, 연간)
    private boolean isRightDirection (GenderType gender, String yearSky) {
        String minusPlus = sajuDataProvider.minusPlusOf(yearSky);
        if(gender.equals(GenderType.MALE)) {
            return minusPlus.equals("양");
        } else {
            return minusPlus.equals("음");
        }
    }

    //절입시간 가져오기
    //순행은 생년월일 뒤에 오는 절입 시간을, 역행은 생년월일 앞에 오는 절입 시간을 가져온다.
    // 예를 들어 2월 13일이 순행인 경우 3월 6일(경칩)을 가져온다. 2월 13일이 역행인 경우 2월 4일(입춘)을 가져온다.
    private LocalDateTime getSeasonStartTime(boolean direction, LocalDateTime solarDateTime) {
        Manse manse = direction?
                manseRepository.findFirstBySeasonStartTimeGreaterThanEqualOrderBySolarDateAsc(solarDateTime)
                        .orElseThrow(()-> new SajuException(SajuErrorCode.NOT_FOUND_SEASON_START_TIME))
                : manseRepository.findFirstBySeasonStartTimeLessThanEqualOrderBySolarDateDesc(solarDateTime)
                .orElseThrow(()-> new SajuException(SajuErrorCode.NOT_FOUND_SEASON_START_TIME));
        return manse.getSeasonStartTime();
    }

    //대운수 및 대운 시작 가져오기
    private BigFortuneResult getBigFortuneNumber(boolean direction, LocalDateTime seasonStartTime, LocalDateTime solarDateTime) {
        double  diffDays = direction?
                Duration.between(solarDateTime, seasonStartTime).toMillis() / (1000.0 * 60 * 60 * 24)   //역행
                : Duration.between(seasonStartTime, solarDateTime).toMillis() / (1000.0 * 60 * 60 * 24);  //순행
        //대운수는 절입일부터 생년월일까지의 시간차를 3
        int divider = (int) Math.floor(diffDays / 3);
        int remainder = ((int) Math.floor(diffDays)) % 3;

        int bigFortuneNumber = divider;
        if(diffDays < 4) bigFortuneNumber = 1;  //3일 미만은 무조건 1대운
        if(remainder == 2) bigFortuneNumber += 1; //나머지가 있으면 올림

        //대운 시작년도
        int bigFortuneStartYear =
                solarDateTime.plusYears(bigFortuneNumber).getYear();
        return new BigFortuneResult(bigFortuneNumber, bigFortuneStartYear);

    }

    public record BigFortuneResult(int bigFortuneNumber, int bigFortuneStartYear){}
}
