package com.weverse.servicec;

import com.weverse.servicec.model.VoteData;
import com.weverse.servicec.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@DataR2dbcTest
@ActiveProfiles("test")
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    public void testSaveAndRetrieveVoteData() {
        // 테스트 데이터 생성
        VoteData data = VoteData.builder()
                .userId("user123")
                .voteId("vote_456")
                .option("A")
                .timestamp(Instant.parse("2024-10-31T12:00:00Z"))  // ISO 8601 형식의 UTC 시간
                .location("Seoul")
                .device("mobile")
                .ageGroup("20-29")
                .gender("M")
                .campaignId("camp_789")
                .sessionId("sess_101112")
                .build();

        // 데이터 저장 및 조회
        Mono<VoteData> saveAndRetrieve = voteRepository.save(data)
                .then(voteRepository.findById(data.getVoteId()));

        StepVerifier.create(saveAndRetrieve)
                .expectNextMatches(v -> v.getVoteId().equals("vote_456"))
                .verifyComplete();
    }
}