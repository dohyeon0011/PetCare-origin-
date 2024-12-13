package com.PetCare.dto.Pet.response;

import com.PetCare.domain.Pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PetReservationResponse {
    private String name;
    private int age;
    private String breed;

    public PetReservationResponse(Pet pet) {
        this.name = pet.getName();
        this.age = pet.getAge();
        this.breed = pet.getBreed();
    }
}
