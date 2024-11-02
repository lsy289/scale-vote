package com.weverse.serviceb.service;

import com.weverse.serviceb.model.VoteData;
import com.weverse.serviceb.repository.VoteDataRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VoteProcessingService {

    private final VoteDataRepository voteDataRepository;
    private final StreamBridge streamBridge;
    private final ExternalService externalService;

    public VoteProcessingService(VoteDataRepository voteDataRepository, StreamBridge streamBridge, ExternalService externalService) {
        this.voteDataRepository = voteDataRepository;
        this.streamBridge = streamBridge;
        this.externalService = externalService;
    }

    public Mono<VoteData> processVoteData(VoteData voteData) {
        return externalService.fetchRegionByUserId(voteData.getUserId())
                .flatMap(region -> {
                    voteData.setRegion(region);  // 외부 데이터 결합
                    return voteDataRepository.save(voteData);
                })
                .doOnSuccess(savedVote -> streamBridge.send("processed-topic", savedVote));
    }
}
