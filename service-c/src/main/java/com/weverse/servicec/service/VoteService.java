package com.weverse.servicec.service;

import com.weverse.servicec.model.VoteData;
import com.weverse.servicec.repository.VoteRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final StreamBridge streamBridge;

    public VoteService(VoteRepository voteRepository, StreamBridge streamBridge) {
        this.voteRepository = voteRepository;
        this.streamBridge = streamBridge;
    }

    public Mono<VoteData> processAndSaveVoteData(VoteData voteData) {
        if ("A".equalsIgnoreCase(voteData.getOption())) {
            voteData.setHighPriority(true);
        }

        // 조건을 만족하는 경우 알람 메시지 전송
        if (voteData.isHighPriority()) {
            sendAlert(voteData);
        }

        // PostgreSQL에 데이터 저장
        return voteRepository.save(voteData);
    }

    private void sendAlert(VoteData voteData) {
        // 알람 메시지를 Kafka의 alert-topic으로 전송
        streamBridge.send("sendAlertData-out-0", "High priority vote detected for user: " + voteData.getUserId());
    }
}