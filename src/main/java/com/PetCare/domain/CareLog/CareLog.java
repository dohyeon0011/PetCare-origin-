package com.PetCare.domain.CareLog;

import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.CareLog.response.CareLogResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CareLog { // 돌봄 케어 로그

    @Id @GeneratedValue
    @Column(name = "care_log_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "sitter_schedule_id")
    private SitterSchedule sitterSchedule;

    @Comment("케어 유형")
    private String careType;

    @Comment("케어 상세 설명")
    private String description;

    @Comment("케어 상세 사진")
    private String imgPath;

    @Comment("로그 작성 시간")
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public CareLog(SitterSchedule sitterSchedule, String careType, String description, String imgPath) {
        addSitterSchedule(sitterSchedule);
        this.careType = careType;
        this.description = description;
        this.imgPath = imgPath;
    }

    // 돌봄사 시점 돌봄 배정 - 돌봄 케어 로그 연관관계 편의 메서드
    public void addSitterSchedule(SitterSchedule sitterSchedule) {
        this.sitterSchedule = sitterSchedule;
    }

    @Comment("케어 로그 내용 수정")
    public void updateCareLog(String careType, String description, String imgPath) {
        this.careType = careType;
        this.description = description;
        this.imgPath = imgPath;
    }

    public CareLogResponse.GetDetail toResponse() {
        return new CareLogResponse.GetDetail(this);
    }

}
