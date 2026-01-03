package com.example.saju.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SajuErrorCode {
    INVALID_CALENDARTYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 역법 타입입니다."),
    INVALID_GENDERTYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 성별 타입입니다."),
    NOT_MATCH_TIME_RANGE(HttpStatus.NOT_FOUND, "시간 데이터에 맞는 범위가 없습니다."),
    //json에서 가져오기
    FAILED_LOAD_SAJUDATA(HttpStatus.INTERNAL_SERVER_ERROR, "데이터를 가져오는데 실패했습니다."),
    NOT_FOUND_MINUS_PLUS_DATA(HttpStatus.NOT_FOUND, "음양 데이터를 찾을 수 없습니다."),
    NOT_FOUND_TIME_RANGE(HttpStatus.NOT_FOUND, "시간 범위 데이터를 찾을 수 없습니다."),
    NOT_FOUND_TIME_PILLAR_DATA(HttpStatus.NOT_FOUND, "시주 데이터를 찾을 수 없습니다."),

    //DB에서 가져오기
    NOT_FOUND_SEASON_START_TIME(HttpStatus.NOT_FOUND, "접입 데이터를 찾을 수 없습니다."),
    SAJU_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Saju Error");

    private final HttpStatus status;
    private final String message;

    SajuErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
