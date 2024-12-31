package com.PetCare.dto.CareLog.response;

import com.PetCare.domain.CareLog.CareLog;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CareLogResponse {

    @NoArgsConstructor
    @Getter
    public static class GetList {
        private long id;
        private String sitterName; // 케어 로그 작성 돌봄자
        private String careType;
        private String description;
        private LocalDateTime createdAt;

        public GetList(CareLog careLog) {
            this.id = careLog.getId();
            this.sitterName = careLog.getSitterSchedule().getSitter().getName();
            this.careType = careLog.getCareType();
            this.description = careLog.getDescription();
            this.createdAt = careLog.getCreatedAt();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail {
        private long id;
        private String sitterName; // 케어 로그 작성 돌봄자
        private String careType;
        private String description;
        private String imgPath;
        private LocalDateTime createdAt;

        public GetDetail(CareLog careLog) {
            this.id = careLog.getId();
            this.sitterName = careLog.getSitterSchedule().getSitter().getName();
            this.careType = careLog.getCareType();
            this.description = careLog.getDescription();
            this.imgPath = careLog.getImgPath();
            this.createdAt = careLog.getCreatedAt();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetNewCareLog {
        private String sitterName;
        private String customerNickName;
        private String careType;
        private String description;
        private String imgPath;

        public GetNewCareLog(SitterSchedule sitterSchedule) {
            this.sitterName = sitterSchedule.getSitter().getName();
            this.customerNickName = sitterSchedule.getCustomer().getNickName();
        }
    }
}
