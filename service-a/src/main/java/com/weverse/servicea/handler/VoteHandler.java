package com.weverse.servicea.handler;

import com.weverse.servicea.model.Vote;
import com.weverse.servicea.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class VoteHandler {

    private final VoteService voteService;

    @Autowired
    public VoteHandler(VoteService voteService) {
        this.voteService = voteService;
    }

    public Mono<ServerResponse> handleVote(ServerRequest request) {
        return request.bodyToMono(Vote.class)
                .flatMap(voteService::processVote)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Failed to process vote."));
    }
}