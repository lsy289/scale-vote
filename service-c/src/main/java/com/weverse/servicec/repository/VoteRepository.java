package com.weverse.servicec.repository;

import com.weverse.servicec.model.VoteData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends ReactiveCrudRepository<VoteData, String> {
}