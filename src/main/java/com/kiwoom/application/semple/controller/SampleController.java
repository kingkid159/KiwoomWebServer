package com.kiwoom.application.semple.controller;

import com.kiwoom.application.semple.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sample")
public class SampleController {

    private final SampleService sampleService;

    private SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(sampleService.login());
    }
}
