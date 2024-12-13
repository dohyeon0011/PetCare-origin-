package com.PetCare.dto.CustomerReservation.response;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.ReservationStatus;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.response.PetReservationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class CustomerReservationResponse {
    private long id;
    private String nickName;
    private LocalDate reservationAt;
    private LocalDateTime createdAt;
    private ReservationStatus status;
    private List<PetReservationResponse> petReservations;

    // 전체 조회
    public CustomerReservationResponse(long id, String nickName, CustomerReservation customerReservation) {
        this.id = id;
        this.nickName = nickName;
        this.reservationAt = customerReservation.getReservationAt();
        this.createdAt = customerReservation.getCreatedAt();
        this.status = customerReservation.getStatus();
    }

    // 예약 상세 조회
    public CustomerReservationResponse(CustomerReservation customerReservation, List<Pet> pets) {
        this.reservationAt = customerReservation.getReservationAt();
        this.createdAt = customerReservation.getCreatedAt();
        this.status = customerReservation.getStatus();
        this.petReservations = pets
                .stream()
                .map(PetReservationResponse::new)
                .collect(Collectors.toList());
    }
}
