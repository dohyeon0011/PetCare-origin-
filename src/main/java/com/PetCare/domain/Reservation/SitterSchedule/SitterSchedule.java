package com.PetCare.domain.Reservation.SitterSchedule;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Reservation.CustomerReservation.ReservationStatus;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Pet.PetReservation;
import com.PetCare.dto.Reservation.SitterSchedule.response.SitterScheduleResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SitterSchedule { // 돌봄 예약(돌봄사 시점)

    @Id @GeneratedValue
    @Column(name = "sitter_schedule_id")
    private long id;

    @Comment("돌봄 예약(고객 시점)")
    @OneToOne
    @JoinColumn(name = "customer_reservation_id")
    @JsonIgnore
    private CustomerReservation customerReservation;

    @Comment("예약한 회원(고객) 번호")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Member customer;

    @Comment("예약된 회원(돌봄사) 번호")
    @ManyToOne
    @JoinColumn(name = "sitter_id")
    private Member sitter;

    @Comment("예약된 날짜")
    @Column(name = "reservation_at")
    private LocalDate reservationAt;

    @Comment("돌봄 예약 비용")
    private int price;

//    @Comment("돌봄에 맡겨지는 반려견 목록")
//    @OneToMany(mappedBy = "sitterSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<SitterPetReservation> sitterPetReservations = new ArrayList<>();

    @Comment("돌봄에 맡겨지는 반려견 목록")
    @OneToMany(mappedBy = "sitterSchedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<PetReservation> petReservations = new ArrayList<>();

    @Comment("예약이 발생한 시간")
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Comment("예약 상태")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Comment("해당 돌봄 예약에 회원이 남긴 평점")
    private Integer rating;

    @Comment("해당 돌봄 예약에 회원이 남긴 리뷰")
    private String comment;

    public static SitterSchedule createSitterReservation(Member customer, Member sitter, CustomerReservation customerReservation, PetReservation... petReservations) {
        authorization(customer, sitter);

        SitterSchedule sitterSchedule = new SitterSchedule();
        sitterSchedule.addCustomer(customer);
        sitterSchedule.addSitter(sitter);

        for (PetReservation petReservation : petReservations) {
            sitterSchedule.addPetReservation(petReservation);
        }
        sitterSchedule.addCustomerReservation(customerReservation);
        sitterSchedule.price = customerReservation.getPrice();
        sitterSchedule.reservationAt = customerReservation.getReservationAt();
        sitterSchedule.status = ReservationStatus.RESERVATION;

        return sitterSchedule;
    }

    public void addCustomer(Member customer) {
        this.customer = customer;
    }

    public void addSitter(Member sitter) {
        this.sitter = sitter;
        sitter.getSitterSchedules().add(this);
    }

    public void addCustomerReservation(CustomerReservation customerReservation) {
        this.customerReservation = customerReservation;
    }

//    public void addSitterPetReservation(SitterPetReservation sitterPetReservation) {
//        this.sitterPetReservations.add(sitterPetReservation);
//        sitterPetReservation.addSitterSchedule(this);
//    }

    public void addPetReservation(PetReservation petReservation) {
        this.petReservations.add(petReservation);
        petReservation.addSitterSchedule(this);
    }

    private static void authorization(Member customer, Member sitter) {
        if (!customer.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("예약은 고객만 가능합니다.");
        }
        if (!sitter.getRole().equals(Role.PET_SITTER)) {
            throw new IllegalArgumentException("돌봄 예약 배정은 돌봄사만 가능합니다.");
        }
    }

    public void changeReservationAt(LocalDate reservationAt) {
        this.reservationAt = reservationAt;
    }

    public void cancel() {
        if (!this.status.equals(ReservationStatus.RESERVATION)) {
            throw new IllegalArgumentException("이미 취소된 예약입니다.");
        }
        this.status = ReservationStatus.CANCEL;
    }

    public SitterScheduleResponse.GetDetail toResponse() {
        return new SitterScheduleResponse.GetDetail(this.customer, this.sitter, this, this.petReservations);
    }

}
