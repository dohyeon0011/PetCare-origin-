package com.PetCare.dto.CustomerReservation.response;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.PetReservation;
import com.PetCare.domain.CustomerReservation.ReservationStatus;
import com.PetCare.domain.Member.Member;
import com.PetCare.dto.Pet.response.PetReservationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerReservationResponse {

    @NoArgsConstructor
    @Getter
    public static class GetList {
        private LocalDate reservationAt;
        private LocalDateTime createdAt;
        private ReservationStatus status;

        public GetList(CustomerReservation customerReservation) {
            this.reservationAt = customerReservation.getReservationAt();
            this.createdAt = customerReservation.getCreatedAt();
            this.status = customerReservation.getStatus();
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

        public GetDetail(Member customer, Member sitter, CustomerReservation customerReservation, List<PetReservation> pets) {
            this.id = customerReservation.getId();
            this.customerId = customer.getId();
            this.customerNickName = customer.getNickName();
            this.sitterId = sitter.getId();
            this.sitterNickName = sitter.getNickName();
            this.reservationAt = customerReservation.getReservationAt();
            this.createdAt = customerReservation.getCreatedAt();
            this.status = customerReservation.getStatus();
            this.petReservations = pets
                    .stream()
                    .map(PetReservationResponse::new)
                    .collect(Collectors.toList());
        }
    }

}
