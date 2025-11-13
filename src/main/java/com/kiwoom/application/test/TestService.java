package com.kiwoom.application.test;

import com.kiwoom.application.test.entity.TestEntity;
import com.kiwoom.application.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public String connectTest(){
        WebClient client = WebClient.create("http://127.0.0.1:8000");

        Mono<String> result = client.get()
                .uri("/api/v1/test/")
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(result.block());
        return result.block();
    }

    public String dbConnectTest(){
        List<TestEntity> test = testRepository.findAll();
        return test.get(0).getTest();
    }
}
