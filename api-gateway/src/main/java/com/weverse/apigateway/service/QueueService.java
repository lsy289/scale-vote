package com.weverse.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QueueService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private static final String QUEUE_KEY = "voteQueue";

    @Autowired
    public QueueService(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 요청을 대기열에 추가
    public Mono<Long> addToQueue(String requestPayload) {
        return redisTemplate.opsForList().rightPush(QUEUE_KEY, requestPayload);
    }

    // 대기열에서 요청을 하나 꺼냄
    public Mono<String> pollQueue() {
        return redisTemplate.opsForList().leftPop(QUEUE_KEY);
    }
}
