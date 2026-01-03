package com.example.saju.controller;

import com.example.saju.dto.SaJuResponseDto;
import com.example.saju.dto.SajuRequestDto;
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
    @GetMapping
    public ResponseEntity<SaJuResponseDto> getSaju(@RequestBody SajuRequestDto requestDto) {
        SaJuResponseDto responseDto = sajuService.convertBirthToSaju(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
}
