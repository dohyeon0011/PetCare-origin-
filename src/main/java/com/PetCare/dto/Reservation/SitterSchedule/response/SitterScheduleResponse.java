package com.PetCare.dto.Reservation.SitterSchedule.response;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.PetReservation;
import com.PetCare.domain.Reservation.CustomerReservation.ReservationStatus;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.Pet.response.PetReservationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SitterScheduleResponse {

    @NoArgsConstructor
    @Getter
    public static class GetList {
        private long id;
        private LocalDate reservationAt;
        private LocalDateTime createdAt;
        private ReservationStatus status;

        public GetList(SitterSchedule sitterSchedule) {
            this.id = sitterSchedule.getId();
            this.reservationAt = sitterSchedule.getReservationAt();
            this.createdAt = sitterSchedule.getCreatedAt();
            this.status = sitterSchedule.getStatus();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail {
        private long id;
        private long customerId;
        private String customerNickName;
        private long sitterId;
        private String sitterNickName;
        private int price;
        private LocalDate reservationAt;
        private LocalDateTime createdAt;
        private ReservationStatus status;
        private List<PetReservationResponse> petReservations;

        public GetDetail(Member customer, Member sitter, SitterSchedule sitterSchedule, List<PetReservation> pets) {
            this.id = sitterSchedule.getId();
            this.customerId = customer.getId();
            this.customerNickName = customer.getNickName();
            this.sitterId = sitter.getId();
            this.sitterNickName = sitter.getNickName();
            this.price = sitterSchedule.getPrice();
            this.reservationAt = sitterSchedule.getReservationAt();
            this.createdAt = sitterSchedule.getCreatedAt();
            this.status = sitterSchedule.getStatus();
            this.petReservations = pets
                    .stream()
                    .map(PetReservationResponse::new)
                    .collect(Collectors.toList());
        }
    }

}
