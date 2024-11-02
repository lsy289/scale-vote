package com.weverse.servicea.router;

import com.weverse.servicea.handler.VoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class VoteRouter {

    @Bean
    public RouterFunction<ServerResponse> route(VoteHandler voteHandler) {
        return RouterFunctions
                .nest(path("/api"),
                        RouterFunctions.route(POST("/vote/data"), voteHandler::handleVote));
    }
}