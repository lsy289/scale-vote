package com.weverse.apigateway.processor;

import com.weverse.apigateway.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class QueueProcessor {

    private final QueueService queueService;
    private final WebClient webClient;

    @Autowired
    public QueueProcessor(QueueService queueService) {
        this.queueService = queueService;
        this.webClient = WebClient.builder().baseUrl("http://localhost:8081").build(); // Service A의 base URL
    }

    // 100ms마다 대기열에서 요청을 꺼내 Service A로 전송
    @Scheduled(fixedRate = 100)
    public void processQueue() {
        queueService.pollQueue()
                .flatMap(this::sendToServiceA)
                .subscribe();
    }

    // Service A로 요청 전송
    private Mono<Void> sendToServiceA(String requestPayload) {
        return webClient.post()
                .uri("/api/vote/data")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
