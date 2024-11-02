package com.weverse.serviceb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalService {

    private final WebClient webClient;

    public ExternalService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://external-service.com/api").build();
    }

    // 외부 API를 통해 region 데이터를 가져온다는 시나리오 샘플
    public Mono<String> fetchRegionByUserId(String userId) {
        return webClient.get()
                .uri("/user/{userId}/region", userId)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Region Not Found");
    }
}
