package com.PetCare.controller.Pet.api;

import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
import com.PetCare.dto.Pet.request.UpdatePetRequest;
import com.PetCare.dto.Pet.response.PetResponse;
import com.PetCare.service.Pet.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class PetApiController {

    private final PetService petService;

    @PostMapping("/{memberId}/pets/new")
    public ResponseEntity<List<Pet>> addPet(@PathVariable("memberId") long id, @RequestBody @Valid List<AddPetRequest> request) {
        List<Pet> pets = petService.save(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pets);
    }

    @GetMapping("/{memberId}/pets")
    public ResponseEntity<List<PetResponse>> findPets(@PathVariable("memberId") long id) {
        List<PetResponse> pets = petService.findById(id);

        return ResponseEntity.ok()
                .body(pets);
    }

    @DeleteMapping("{memberId}/pets/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("memberId") long id, @PathVariable("petId") long petId) {
        petService.delete(id, petId);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("{memberId}/pets")
    public ResponseEntity<List<PetResponse>> updatePet(@PathVariable("memberId") long id, @RequestBody @Valid List<UpdatePetRequest> requests) {
        List<PetResponse> updatePets = petService.update(id, requests);

        return ResponseEntity.ok()
                .body(updatePets);
    }

}
