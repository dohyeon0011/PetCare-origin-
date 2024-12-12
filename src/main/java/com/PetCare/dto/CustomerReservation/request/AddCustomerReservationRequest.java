package com.PetCare.dto.CustomerReservation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCustomerReservationRequest {

    private long memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationAt;

    private List<Long> petIds;
}
