package com.weverse.servicec.consumer;

import com.weverse.servicec.model.VoteData;
import com.weverse.servicec.service.VoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class VoteConsumer {

    private final VoteService voteService;

    public VoteConsumer(VoteService voteService) {
        this.voteService = voteService;
    }

    @Bean
    public Consumer<VoteData> processVoteData() {
        return voteData -> voteService.processAndSaveVoteData(voteData)
                .doOnSuccess(savedData -> System.out.println("Processed and saved VoteData: " + savedData))
                .doOnError(error -> System.err.println("Failed to save VoteData: " + error.getMessage()))
                .subscribe();
    }
}
