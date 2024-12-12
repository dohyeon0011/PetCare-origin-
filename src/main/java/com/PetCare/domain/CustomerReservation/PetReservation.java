package com.PetCare.domain.CustomerReservation;

import com.PetCare.domain.Pet.Pet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PetReservation { // Pet(반려견)과 CustomerReservation(고객의 돌봄 예약) 중간 매핑 테이블

    @Id
    @GeneratedValue
    @Column(name = "pet_reservation_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_reservation_id")
    @JsonIgnore // 양방향 관계에서 엔티티로 직접 조회시 무한루프 피하려고
    private CustomerReservation customerReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    public static PetReservation createPetReservation(Pet pet) {
        PetReservation petReservation = new PetReservation();
        petReservation.addPet(pet);

        return petReservation;
    }

    public void addPet(Pet pet) {
        this.pet = pet;
    }

    public void addCustomerReservation(CustomerReservation customerReservation) {
        this.customerReservation = customerReservation;
    }

}
