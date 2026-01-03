package com.example.saju.provider;

import com.example.saju.dto.Pillar;
import com.example.saju.exception.SajuErrorCode;
import com.example.saju.exception.SajuException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SajuDataProviderImpl implements  SajuDataProvider {
    private final ObjectMapper objectMapper;
    private JsonNode root; // 앱 시작 시 1회 로딩 후 불변처럼 사용

    Logger logger = LoggerFactory.getLogger(SajuDataProviderImpl.class);

    private static final DateTimeFormatter HH_MM = DateTimeFormatter.ofPattern("HH:mm");

    // 앱 시작 시 딱 한 번 실행
    @PostConstruct
    public void init() {
        try (InputStream is  = new ClassPathResource("data/saju-data.json").getInputStream()){
            this.root = objectMapper.readTree(is);
        } catch (Exception e) {
            logger.info("Failed to load saju-data.json: {}", e.getMessage());
            logger.error("SajuDataProvider init failed", e);
            throw new SajuException(SajuErrorCode.FAILED_LOAD_SAJUDATA);
        }
    }


    @Override
    public String minusPlusOf(String yearSky) {
        JsonNode node = root.path("minusPlus").path(yearSky);
        if (node.isMissingNode() || node.isNull()) {
            throw new SajuException(SajuErrorCode.NOT_FOUND_MINUS_PLUS_DATA, "::: yearSky: " + yearSky);
        }
        return node.asText();
    }

    //12지지 시간 구하기
    public String timeIndexOf(LocalTime time) {
        JsonNode timeJuRange = root.path("timeJuRange");
        if (timeJuRange.isMissingNode() || timeJuRange.isNull()) throw new SajuException(SajuErrorCode.NOT_FOUND_TIME_RANGE, "::: time: " + time);

        //자시
        if(time.isBefore(LocalTime.of(1,30))||time.isAfter(LocalTime.of(23,29))) return "0";
        int hour = ((time.getHour() - 1) / 2) + 1;
        for (int i = hour-1; i<=hour+1; i++) {
            if (i < 0 || i > 11) continue;  //실수로 12가 나올경우를 방지

            String key = String.valueOf(i);
            JsonNode node = timeJuRange.path(key);
            //시간 범위 파싱 및 확인
            LocalTime start = LocalTime.parse(node.path("0").asText(), HH_MM);
            LocalTime end = LocalTime.parse(node.path("1").asText(), HH_MM);
            if (!time.isBefore(start) && !time.isAfter(end)) return key;
        }
        throw new SajuException(SajuErrorCode.NOT_MATCH_TIME_RANGE, "::: time: " + time);
    }

    @Override
    public Pillar getTimePillar(String daySky, LocalTime time) {
        JsonNode timeJu = root.path("timeJuByDayStem");
        if (timeJu.isMissingNode() || timeJu.isNull()) throw new SajuException(SajuErrorCode.NOT_FOUND_TIME_PILLAR_DATA);
        String timeIndex = timeIndexOf(time);
        JsonNode node = timeJu.path(daySky).path(timeIndex);
        //시간주 데이터 확인
        return new Pillar(node.path("0").asText(), node.path("1").asText());
    }
}
