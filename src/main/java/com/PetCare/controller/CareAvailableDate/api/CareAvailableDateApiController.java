package com.PetCare.controller.CareAvailableDate.api;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.dto.CareAvailableDate.request.AddCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.request.UpdateCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import com.PetCare.service.CareAvailableDate.CareAvailableDateService;
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
public class CareAvailableDateApiController {

    private final CareAvailableDateService careAvailableDateService;

    @Comment("돌봄 가능 일정 등록")
    @PostMapping("/{memberId}/careAvailableDate/new")
    public ResponseEntity<CareAvailableDate> saveCareAvailability(@PathVariable("memberId") long id,
                                                                  @RequestBody @Valid AddCareAvailableDateRequest request) {
        CareAvailableDate careAvailableDate = careAvailableDateService.save(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(careAvailableDate);
    }

    @Comment("특정 회원의 등록한 모든 돌봄 일정 조회")
    @GetMapping("/{memberId}/careAvailableDateList")
    public ResponseEntity<List<CareAvailableDateResponse>> findCareAvailabilityList(@PathVariable("memberId") long id) {
        List<CareAvailableDateResponse> sitterAvailableDateList = careAvailableDateService.findAllById(id);

        return ResponseEntity.ok()
                .body(sitterAvailableDateList);
    }

    @Comment("특정 회원의 등록한 돌봄 일정 단건 조회")
    @GetMapping("/{memberId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<CareAvailableDateResponse> findSitterAvailableDate(@PathVariable("memberId") long id,
                                                                             @PathVariable("careAvailableDateId") long careAvailableDateId) {
        CareAvailableDateResponse sitterAvailableDate = careAvailableDateService.findById(id, careAvailableDateId);

        return ResponseEntity.ok()
                .body(sitterAvailableDate);
    }

    @Comment("특정 회원의 등록한 특정 돌봄 일정 삭제")
    @DeleteMapping("/{memberId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<Void> deleteCareAvailability(@PathVariable("memberId") long id,
                                                       @PathVariable("careAvailableDateId") long careAvailableDateId) {
        careAvailableDateService.delete(id, careAvailableDateId);

        return ResponseEntity.ok()
                .build();
    }

    @Comment("특정 회원의 등록한 특정 돌봄 일정 수정")
    @PutMapping("/{memberId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<CareAvailableDateResponse> updateCareAvailability(@PathVariable("memberId") long id,
                                                                            @PathVariable("careAvailableDateId") long careAvailableDateId,
                                                                            @RequestBody @Valid UpdateCareAvailableDateRequest request) {
        CareAvailableDateResponse updateSitterAvailableDate = careAvailableDateService.update(id, careAvailableDateId, request);

        return ResponseEntity.ok()
                .body(updateSitterAvailableDate);
    }

}
