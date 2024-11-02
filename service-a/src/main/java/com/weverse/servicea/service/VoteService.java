package com.weverse.servicea.service;

import com.weverse.servicea.model.Vote;
import com.weverse.servicea.repository.VoteRepository;
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

    public Mono<String> processVote(Vote request) {
        return voteRepository.save(request)
                .doOnSuccess(saved -> streamBridge.send("output-vote-topic", request)) // Kafka로 전송
                .map(saved -> "Vote saved and sent to B service");
    }
}
