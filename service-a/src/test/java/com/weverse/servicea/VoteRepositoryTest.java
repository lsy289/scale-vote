package com.weverse.servicea;

import com.weverse.servicea.model.Vote;
import com.weverse.servicea.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@DataR2dbcTest
@ActiveProfiles("test")  // 테스트 프로파일 사용
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    public void testSaveAndRetrieveVote() {
        Vote vote = Vote.builder()
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

        // PostgreSQL에 저장 및 검증
        Mono<Vote> saveAndRetrieve = voteRepository.save(vote)
                .flatMap(savedData -> voteRepository.findById(savedData.getVoteId()));

        StepVerifier.create(saveAndRetrieve)
                .expectNextMatches(retrievedData ->
                        retrievedData.getUserId().equals("user123") &&
                                retrievedData.getOption().equals("A") &&
                                retrievedData.getLocation().equals("Seoul")
                )
                .verifyComplete();
    }
}