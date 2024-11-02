package com.weverse.serviceb;

import com.weverse.serviceb.model.VoteData;
import com.weverse.serviceb.repository.VoteDataRepository;
import com.weverse.serviceb.service.VoteProcessingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.Mockito.*;

@SpringBootTest
public class VoteProcessingServiceTest {

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private VoteDataRepository voteDataRepository;

    @InjectMocks
    private VoteProcessingService voteProcessingService;

    @Test
    public void testProcessVoteWithTransformation() {
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

        // 저장된 데이터를 MongoDB에 저장하고 새로운 필드 추가 후 전송
        when(voteDataRepository.save(data)).thenReturn(Mono.just(data));
        voteProcessingService.processVoteData(data);

        verify(streamBridge, times(1)).send(eq("processed-topic"), any(VoteData.class));
    }
}