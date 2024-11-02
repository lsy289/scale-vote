package com.weverse.apigateway.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {

    private String userId;        // 사용자 ID
    private String voteId;        // 투표 ID
    private String option;        // 선택된 옵션 (예: "A", "B")
    private Instant timestamp;    // 투표 타임스탬프
    private String location;      // 위치 정보 (예: 도시명)
    private String device;        // 사용 디바이스 (예: "mobile", "desktop")
    private String ageGroup;      // 연령대 (예: "20-29")
    private String gender;        // 성별 (예: "M", "F")
    private String campaignId;    // 캠페인 ID
    private String sessionId;     // 세션 ID
}
