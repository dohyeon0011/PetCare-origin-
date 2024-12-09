package com.PetCare.controller.Pet.api;

import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
import com.PetCare.dto.Pet.request.UpdatePetRequest;
import com.PetCare.dto.Pet.response.PetResponse;
import com.PetCare.service.Pet.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class PetApiController {

    private final PetService petService;

    @Comment("반려견 등록")
    @PostMapping("/{memberId}/pets/new")
    public ResponseEntity<List<Pet>> addPet(@PathVariable("memberId") long id, @RequestBody @Valid List<AddPetRequest> requests) {
        List<Pet> pets = petService.save(id, requests);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pets);
    }

    @Comment("특정 회원의 모든 반려견 조회")
    @GetMapping("/{memberId}/pets")
    public ResponseEntity<List<PetResponse>> findPets(@PathVariable("memberId") long id) {
        List<PetResponse> pets = petService.findById(id);

        return ResponseEntity.ok()
                .body(pets);
    }

    @Comment("특정 회원의 특정 반려견 삭제")
    @DeleteMapping("{memberId}/pets/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("memberId") long id, @PathVariable("petId") long petId) {
        petService.delete(id, petId);

        return ResponseEntity.ok()
                .build();
    }

    @Comment("특정 회원의 반려견 정보 수정")
    @PutMapping("{memberId}/pets")
    public ResponseEntity<List<PetResponse>> updatePet(@PathVariable("memberId") long id, @RequestBody @Valid List<UpdatePetRequest> requests) {
        List<PetResponse> updatePets = petService.update(id, requests);

        return ResponseEntity.ok()
                .body(updatePets);
    }

}
