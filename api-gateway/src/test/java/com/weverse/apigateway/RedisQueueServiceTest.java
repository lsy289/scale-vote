package com.weverse.apigateway;

import com.weverse.apigateway.service.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataRedisTest
@ActiveProfiles("test")
public class RedisQueueServiceTest {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private QueueService queueService;

    @BeforeEach
    public void setUp() {
        // Redis 연결을 사용하여 QueueService 인스턴스 생성
        queueService = new QueueService(redisTemplate);
    }

    @Test
    public void testAddToQueueAndPollQueue() {
        // 대기열에 데이터 추가
        Mono<Long> addToQueueMono = queueService.addToQueue("testRequest");

        // 추가된 데이터 확인 및 대기열에서 데이터 꺼내기 테스트
        StepVerifier.create(addToQueueMono)
                .expectNext(1L)  // 대기열에 1개의 데이터가 추가되어 크기가 1임을 기대
                .verifyComplete();

        Mono<String> pollQueueMono = queueService.pollQueue();

        StepVerifier.create(pollQueueMono)
                .expectNext("testRequest")  // 대기열에서 추가한 데이터가 꺼내짐을 기대
                .verifyComplete();
    }
}
