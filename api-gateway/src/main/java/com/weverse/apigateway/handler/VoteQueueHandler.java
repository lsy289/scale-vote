package com.weverse.apigateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weverse.apigateway.model.request.VoteRequest;
import com.weverse.apigateway.service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vote")
public class VoteQueueHandler {

    private final QueueService queueService;
    private final ObjectMapper objectMapper;

    public VoteQueueHandler(QueueService queueService, ObjectMapper objectMapper) {
        this.queueService = queueService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> handleVoteRequest(@RequestBody VoteRequest request) {
        // 클라이언트의 투표 요청을 대기열에 추가
        try {
            // VoteData 객체를 JSON 문자열로 변환
            String requestPayload = objectMapper.writeValueAsString(request);

            // 클라이언트의 투표 요청을 대기열에 추가
            return queueService.addToQueue(requestPayload)
                    .map(queueSize -> ResponseEntity.ok("Request added to queue. Queue size: " + queueSize))
                    .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Failed to add request to queue")));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(500).body("Failed to process request"));
        }
    }

    @GetMapping("/fallback")
    public Mono<ResponseEntity<String>> fallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Service is temporarily unavailable. Please try again later."));
    }
}
