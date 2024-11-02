package com.weverse.servicea.repository;

import com.weverse.servicea.model.Vote;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends ReactiveCrudRepository<Vote, String> {
}