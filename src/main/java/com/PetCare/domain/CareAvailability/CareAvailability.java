package com.PetCare.domain.CareAvailability;

import com.PetCare.domain.Member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CareAvailability { // 예약 가능 날짜(돌봄사)

    @Id @GeneratedValue
    @Column(name = "care_availability_id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("예약 가능한 날짜")
    @Column(name = "availability_at")
    private Date availabilityAt;

    @Comment("예약 가능 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus status;

    // 돌봄사-예약 가능 날짜 연관관계 편의 메서드
    public void addPetSitter(Member member) {
        this.member = member;
        member.getCareAvailabilities().add(this);
    }

    @Builder
    public CareAvailability(Date availabilityAt) {
        this.availabilityAt = availabilityAt;
        this.status = AvailabilityStatus.POSSIBILITY;
    }

}
