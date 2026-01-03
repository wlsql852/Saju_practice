package com.example.saju.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="manses")
@NoArgsConstructor
@Getter
public class Manse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate solarDate;

    private LocalDate lunarDate;

    @Column(length = 10)
    private String season;   //절기

    private LocalDateTime seasonStartTime;    //절기 시작 시간

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean leapMonth;   //윤달 여부

    @Column(length = 10)
    private String yearSky;    //연주의 천간

    @Column(length = 10)
    private String yearGround; //연주의 지지

    @Column(length = 10)
    private String monthSky;   //월주의 천간

    @Column(length = 10)
    private String monthGround;  //월주의 지지

    @Column(length = 10)
    private String daySky;     //일주의 천간

    @Column(length = 10)
    private String dayGround;  //일주의 지지
}
