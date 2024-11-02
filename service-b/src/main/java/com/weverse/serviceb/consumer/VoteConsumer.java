package com.weverse.serviceb.consumer;

import com.weverse.serviceb.model.VoteData;
import com.weverse.serviceb.service.VoteProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class VoteConsumer {

    private final VoteProcessingService voteProcessingService;

    public VoteConsumer(VoteProcessingService voteProcessingService) {
        this.voteProcessingService = voteProcessingService;
    }

    @Bean
    public Consumer<VoteData> voteDataConsumer() {
        return voteData -> {
            voteProcessingService.processVoteData(voteData).subscribe();
        };
    }
}