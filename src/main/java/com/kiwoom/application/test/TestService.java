package com.kiwoom.application.test;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TestService {
    public String connectTest(){
        WebClient client = WebClient.create("http://127.0.0.1:8000");

        Mono<String> result = client.get()
                .uri("/api/v1/test/")
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(result.block());
        return result.block();
    }
}
