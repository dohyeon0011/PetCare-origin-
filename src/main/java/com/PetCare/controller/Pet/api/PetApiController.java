package com.PetCare.controller.Pet.api;

import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
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

}
