package com.weverse.servicec;

import com.weverse.servicec.model.VoteData;
import com.weverse.servicec.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"alert-topic"})
@ActiveProfiles("test")
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void testProcessAndSendAlert() {
        // 알람 전송 조건에 맞는 테스트 데이터 생성
        VoteData voteData = VoteData.builder()
                .userId("user123")
                .voteId("vote_456")
                .option("A")
                .timestamp(Instant.now())
                .location("Seoul")
                .device("mobile")
                .ageGroup("20-29")
                .gender("M")
                .campaignId("camp_789")
                .sessionId("sess_101112")
                .option("A")
                .build();

        // 데이터 저장 후 알람 메시지 전송
        Mono<VoteData> processedVote = voteService.processAndSaveVoteData(voteData);

        StepVerifier.create(processedVote)
                .expectNextMatches(savedData -> {
                    // 데이터 저장 확인 및 highPriority 설정 확인
                    assertThat(savedData.isHighPriority()).isTrue();
                    return true;
                })
                .verifyComplete();
    }
}