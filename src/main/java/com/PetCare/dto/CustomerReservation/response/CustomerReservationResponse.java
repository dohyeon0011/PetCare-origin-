package com.PetCare.dto.CustomerReservation.response;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CustomerReservationResponse {
    private LocalDate reservationAt;
    private LocalDateTime createdAt;
    private ReservationStatus status;

    public CustomerReservationResponse(CustomerReservation customerReservation) {
        this.reservationAt = customerReservation.getReservationAt();
        this.createdAt = customerReservation.getCreatedAt();
        this.status = customerReservation.getStatus();
    }
}
