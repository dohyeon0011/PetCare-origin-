package com.petcare.domain.careAvailabledate;

import com.petcare.domain.member.Member;
import com.petcare.dto.careavailabledate.response.CareAvailableDateResponse;
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
    @JoinColumn(name = "sitter_id")
    private Member sitter;

    @Comment("예약 가능한 날짜")
//    @DateTimeFormat(pattern = "yyyy-MM-dd") // 이 어노테이션은 주로 컨트롤러에서 바인딩할 때 사용됨, 엔티티는 가급적이면 데이터베이스 매핑에만 집중을 권장
    @Column(name = "available_at", unique = true)
    private LocalDate availableAt;

    @Comment("돌봄 비용")
    private int price;

    @Comment("예약 가능 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareAvailableDateStatus status;

    // 돌봄사-예약 가능 날짜 연관관계 편의 메서드
    public void addPetSitter(Member sitter) {
        this.sitter = sitter;
        sitter.getCareAvailabilities().add(this);
    }

    @Builder
    public CareAvailableDate(LocalDate availabilityAt, int price) {
        this.availableAt = availabilityAt;
        this.price = price;
        this.status = CareAvailableDateStatus.POSSIBILITY;
    }

    public void update(LocalDate availabilityAt, int price) {
        verifyingStatus();

        this.availableAt = availabilityAt;
        this.price = price;
    }

    // 예약 상태
    public void reservation() {
        if (!this.status.equals(CareAvailableDateStatus.POSSIBILITY)) {
            throw new IllegalArgumentException("요청하신 날짜는 이미 예약된 날짜입니다.");
        }
        this.status = CareAvailableDateStatus.IMPOSSIBILITY;
    }

    // 예약 취소
    public void cancel() {
        if (!this.status.equals(CareAvailableDateStatus.IMPOSSIBILITY)) {
            throw new IllegalArgumentException("요청하신 날짜는 이미 취소된 예약입니다.");
        }
        this.status = CareAvailableDateStatus.POSSIBILITY;
    }

    public CareAvailableDateResponse.GetList toResponse() {
        return new CareAvailableDateResponse.GetList(this);
    }

    @Comment("예약 상태 확인")
    private void verifyingStatus() {
        if (!this.status.equals(CareAvailableDateStatus.POSSIBILITY)) {
            throw new IllegalArgumentException("돌봄 예약이 배정된 상태라 수정이 불가합니다.");
        }
    }

}
