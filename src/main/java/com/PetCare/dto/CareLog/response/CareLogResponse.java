package com.PetCare.dto.CareLog.response;

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

        public GetList(long id, String sitterName, String careType, String description, LocalDateTime createdAt) {
            this.id = id;
            this.sitterName = sitterName;
            this.careType = careType;
            this.description = description;
            this.createdAt = createdAt;
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

        /*public GetDetail(CareLog careLog) {
            this.id = careLog.getId();
            this.sitterName = careLog.getSitterSchedule().getSitter().getName();
            this.careType = careLog.getCareType();
            this.description = careLog.getDescription();
            this.imgPath = careLog.getImgPath();
            this.createdAt = careLog.getCreatedAt();
        }*/

        public GetDetail(long id, String sitterName, String careType, String description, String imgPath, LocalDateTime createdAt) {
            this.id = id;
            this.sitterName = sitterName;
            this.careType = careType;
            this.description = description;
            this.imgPath = imgPath;
            this.createdAt = createdAt;
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

        /*public GetNewCareLog(SitterSchedule sitterSchedule) {
            this.sitterName = sitterSchedule.getSitter().getName();
            this.customerNickName = sitterSchedule.getCustomer().getNickName();
        }*/

        public GetNewCareLog(String sitterName, String customerNickName) {
            this.sitterName = sitterName;
            this.customerNickName = customerNickName;
        }
    }
}
