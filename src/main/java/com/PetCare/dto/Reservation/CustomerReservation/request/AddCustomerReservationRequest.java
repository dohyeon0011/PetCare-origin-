package com.PetCare.dto.Reservation.CustomerReservation.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCustomerReservationRequest {

    private long customerId; // 고객
    private long sitterId; // 돌봄사

    @NotNull(message = "날짜 선택은 필수입니다.")
    private long careAvailableId;

    @NotNull(message = "돌봄 예약 비용은 필수 값입니다.")
    private int price;

    @NotEmpty(message = "돌봄을 맡길 반려견 선택은 필수입니다.")
    private List<Long> petIds;
}
