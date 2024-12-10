package com.PetCare.dto.CareAvailability.response;

import com.PetCare.domain.CareAvailability.AvailabilityStatus;
import com.PetCare.domain.CareAvailability.CareAvailability;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CareAvailabilityResponse {

    private LocalDate availabilityAt;
    private int price;
    private AvailabilityStatus status;

    public CareAvailabilityResponse(CareAvailability careAvailability) {
        this.availabilityAt = careAvailability.getAvailabilityAt();
        this.price = careAvailability.getPrice();
        this.status = careAvailability.getStatus();
    }

}
