package com.PetCare.repository.Pet;

import com.PetCare.domain.Pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
