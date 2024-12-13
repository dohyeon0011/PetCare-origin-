package com.PetCare.dto.CareAvailableDate.response;

import com.PetCare.domain.CareAvailableDate.CareAvailableDateStatus;
import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CareAvailableDateResponse {
    private long id;
    private LocalDate availabilityAt;
    private int price;
    private CareAvailableDateStatus status;

    public CareAvailableDateResponse(CareAvailableDate careAvailableDate) {
        this.id = careAvailableDate.getId();
        this.availabilityAt = careAvailableDate.getAvailableAt();
        this.price = careAvailableDate.getPrice();
        this.status = careAvailableDate.getStatus();
    }

}
