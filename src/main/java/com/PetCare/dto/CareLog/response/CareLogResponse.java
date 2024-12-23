package com.PetCare.dto.CareLog.response;

import com.PetCare.domain.CareLog.CareLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CareLogResponse {

    private long id;
    private String sitterName; // 케어 로그 작성 돌봄자
    private String careType;
    private String description;
    private String imgPath;
    private LocalDateTime createdAt;

    public CareLogResponse(CareLog careLog) {
        this.sitterName = careLog.getSitterSchedule().getSitter().getName();
        this.careType = careLog.getCareType();
        this.description = careLog.getDescription();
        this.imgPath = careLog.getImgPath();
        this.createdAt = careLog.getCreatedAt();
    }

}
