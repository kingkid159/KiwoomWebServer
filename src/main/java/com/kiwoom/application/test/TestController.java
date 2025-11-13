package com.kiwoom.application.test;

import com.kiwoom.application.common.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.success(testService.connectTest());
    }

    @GetMapping("/db")
    public ResponseEntity<TestDto> db() {
        return ResponseEntity.success(new TestDto(testService.dbConnectTest()));
    }
}
