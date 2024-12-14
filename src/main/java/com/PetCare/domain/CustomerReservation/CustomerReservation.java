package com.PetCare.domain.CustomerReservation;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.dto.CustomerReservation.response.CustomerReservationResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CustomerReservation { // 돌봄 예약(고객 시점)

    @Id
    @GeneratedValue
    @Column(name = "customer_reservation_id", updatable = false)
    private long id;

    @Comment("예약한 회원(고객) 번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Member customer;

    @Comment("예약된 회원(돌봄사) 번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_id")
    private Member sitter;

    @OneToMany(mappedBy = "customerReservation", cascade = CascadeType.ALL)
    private List<PetReservation> petReservations = new ArrayList<>();

    @Comment("예약된 날짜")
    @Column(name = "reservation_at")
    private LocalDate reservationAt;

    @Comment("예약이 발생한 시간")
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Comment("예약 상태")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public static CustomerReservation createCustomerReservation(Member customer, Member sitter, PetReservation... petReservations) {
        authorization(customer);

        CustomerReservation customerReservation = new CustomerReservation();
        customerReservation.addCustomer(customer);
        customerReservation.addSitter(sitter);

        for (PetReservation petReservation : petReservations) {
            customerReservation.addPetReservation(petReservation);
        }
        customerReservation.status = ReservationStatus.RESERVATION;

        return customerReservation;
    }

    // 예약(고객 시점) - 회원(고객) 연관관계 편의 메서드
    public void addCustomer(Member customer) {
        this.customer = customer;
        customer.getCustomerReservations().add(this);
    }

    // 예약(고객 시점) - 회원(돌봄사) 연관관계 편의 메서드
    public void addSitter(Member sitter) {
        this.sitter = sitter;
        sitter.getSitterReservations().add(this);
    }

    // 예약(고객 시점) - 중간 매핑 테이블 연관관계 편의 메서드
    public void addPetReservation(PetReservation petReservation) {
        this.petReservations.add(petReservation);
        petReservation.addCustomerReservation(this);
    }

    // 고객이 예약한 날짜 설정
    public void changeReservationAt(LocalDate reservationAt) {
        this.reservationAt = reservationAt;
    }

    // 예약 취소
    public void cancel() {
        if (!this.status.equals(ReservationStatus.CANCEL)) {
            throw new IllegalArgumentException("이미 취소된 예약입니다.");
        }
        this.status = ReservationStatus.CANCEL;
    }

    private static void authorization(Member member) {
        if (!member.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("예약은 고객만 가능합니다.");
        }
    }

    // 해당 예약 상세 조회
    public CustomerReservationResponse.GetDetail toResponse() {
        return new CustomerReservationResponse.GetDetail(this.customer, this.sitter, this, this.petReservations);
    }
}

