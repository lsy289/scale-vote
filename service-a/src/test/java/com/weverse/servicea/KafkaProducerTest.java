package com.weverse.servicea;

import com.weverse.servicea.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"vote-topic"})
@ActiveProfiles("test")
public class KafkaProducerTest {

    @Autowired
    private StreamBridge streamBridge;

    @Test
    public void testSendVoteData() {
        Vote vote = Vote.builder()
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
                .build();

        boolean sent = streamBridge.send("vote-topic", vote);

        // 메시지 전송 결과 확인
        assertThat(sent).isTrue();
    }
}
