package com.PetCare.dto.Reservation;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import com.PetCare.dto.Pet.response.PetResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ReservationResponse { // 고객이 예약할 때 보여줄 정보
    private long sitterId;
    private String sitterName;
    private List<CareAvailableDateResponse.GetReservation> careAvailableDates;
    private String zipcode;
    private String address;
    private List<PetResponse.GetReservation> pets;

    public ReservationResponse(Member sitter, List<CareAvailableDate> careAvailableDates, List<Pet> pets) {
        this.sitterId = sitter.getId();
        this.sitterName = sitter.getName();
        this.zipcode = sitter.getZipcode();
        this.address = sitter.getAddress();
        this.careAvailableDates = careAvailableDates
                .stream()
                .map(CareAvailableDateResponse.GetReservation::new)
                .toList();
        this.pets = pets
                .stream()
                .map(PetResponse.GetReservation::new)
                .toList();
    }

}
