package com.PetCare.dto.Pet.response;

import com.PetCare.domain.CustomerReservation.PetReservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PetReservationResponse {
    private String name;
    private int age;
    private String breed;

    public PetReservationResponse(PetReservation petReservation) {
        this.name = petReservation.getPet().getName();
        this.age = petReservation.getPet().getAge();
        this.breed = petReservation.getPet().getBreed();
    }
}
