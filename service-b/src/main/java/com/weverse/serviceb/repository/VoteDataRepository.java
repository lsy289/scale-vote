package com.weverse.serviceb.repository;

import com.weverse.serviceb.model.VoteData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDataRepository extends ReactiveMongoRepository<VoteData, String> {
}