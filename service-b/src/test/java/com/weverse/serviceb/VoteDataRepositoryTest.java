package com.weverse.serviceb;

import com.weverse.serviceb.model.VoteData;
import com.weverse.serviceb.repository.VoteDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@DataMongoTest
@ActiveProfiles("test")  // test 프로파일을 사용하여 설정 분리 가능
public class VoteDataRepositoryTest {

    @Autowired
    private VoteDataRepository voteDataRepository;

    @Test
    public void testSaveAndRetrieveVoteData() {
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
                .region("Gwacheon")
                .build();

        // MongoDB에 저장 및 검증
        // 저장 후 findById로 조회하여 검증
        Mono<VoteData> saveAndRetrieve = voteDataRepository.save(data)
                .then(voteDataRepository.findById(data.getVoteId()));

        StepVerifier.create(saveAndRetrieve)
                .expectNextMatches(v -> v.getVoteId().equals("vote_456"))
                .verifyComplete();
    }
}
