package com.PetCare.dto.Pet.response;

import com.PetCare.domain.Pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PetResponse {
    private long id;
    private String name;
    private int age;
    private String breed;
    private String medicalConditions;
    private String profileImgUrl;

    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.age = pet.getAge();
        this.breed = pet.getBreed();
        this.medicalConditions = pet.getMedicalConditions();
        this.profileImgUrl = pet.getProfileImgUrl();
    }
}
