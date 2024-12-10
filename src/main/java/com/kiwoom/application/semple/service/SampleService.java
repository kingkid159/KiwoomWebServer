package com.kiwoom.application.semple.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SampleService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String login() {
        String url = "http://localhost:5000/";

        return restTemplate.getForObject(url, String.class);
    }
}
