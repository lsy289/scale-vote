package com.weverse.servicec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteData {

    @Id
    private String voteId;
    private String userId;
    private String option;
    private Instant timestamp;
    private String location;
    private String device;
    private String ageGroup;
    private String gender;
    private String campaignId;
    private String sessionId;

    // 가공된 데이터 필드 추가
    private boolean highPriority; // 특정 조건에 따라 설정
}
