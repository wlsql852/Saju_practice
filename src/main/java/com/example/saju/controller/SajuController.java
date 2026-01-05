package com.example.saju.controller;

import com.example.saju.dto.FiveElementSimpleResponseDto;
import com.example.saju.dto.SaJuResponseDto;
import com.example.saju.dto.BirthRequestDto;
import com.example.saju.dto.SajuRequestDto;
import com.example.saju.service.FiveElementService;
import com.example.saju.service.SajuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saju")
public class SajuController {
    private final SajuService sajuService;
    private final FiveElementService fiveElementService;

    @GetMapping
    public ResponseEntity<SaJuResponseDto> getSaju(@RequestBody BirthRequestDto requestDto) {
        SaJuResponseDto responseDto = sajuService.convertBirthToSaju(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/five-element/saju")
    public ResponseEntity<FiveElementSimpleResponseDto> getFiveElement(@RequestBody SajuRequestDto requestDto) {
        FiveElementSimpleResponseDto fiveElement = fiveElementService.getFiveElement(requestDto);
        return ResponseEntity.ok().body(fiveElement);
    }
}
