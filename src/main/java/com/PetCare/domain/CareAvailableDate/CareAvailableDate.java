package com.PetCare.domain.CareAvailableDate;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CareAvailableDate { // 예약 가능 날짜(돌봄사)

    @Id @GeneratedValue
    @Column(name = "care_available_date_id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("예약 가능한 날짜")
//    @DateTimeFormat(pattern = "yyyy-MM-dd") // 이 어노테이션은 주로 컨트롤러에서 바인딩할 때 사용됨, 엔티티는 가급적이면 데이터베이스 매핑에만 집중을 권장
    @Column(name = "availability_at")
    private LocalDate availabilityAt;

    @Comment("돌봄 비용")
    private int price;

    @Comment("예약 가능 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareAvailableDateStatus status;

    // 돌봄사-예약 가능 날짜 연관관계 편의 메서드
    public void addPetSitter(Member member) {
        this.member = member;
        member.getCareAvailabilities().add(this);
    }

    @Builder
    public CareAvailableDate(LocalDate availabilityAt, int price) {
        this.availabilityAt = availabilityAt;
        this.price = price;
        this.status = CareAvailableDateStatus.POSSIBILITY;
    }

    public void update(LocalDate availabilityAt, int price) {
        verifyingStatus();

        this.availabilityAt = availabilityAt;
        this.price = price;
    }

    public void assigned() { // 테스트용 : 예약 상태 변경(불가능)
        this.status = CareAvailableDateStatus.IMPOSSIBILITY;
    }

    public CareAvailableDateResponse toResponse() {
        return new CareAvailableDateResponse(this);
    }

    @Comment("예약 상태 확인")
    private void verifyingStatus() {
        if (!this.status.equals(CareAvailableDateStatus.POSSIBILITY)) {
            throw new IllegalArgumentException("돌봄 예약이 배정된 상태라 수정이 불가합니다.");
        }
    }

}
