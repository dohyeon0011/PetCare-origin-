package com.PetCare.controller.CareAvailability.api;

import com.PetCare.domain.CareAvailability.CareAvailability;
import com.PetCare.dto.CareAvailability.request.AddCareAvailabilityRequest;
import com.PetCare.dto.CareAvailability.request.UpdateCareAvailabilityRequest;
import com.PetCare.dto.CareAvailability.response.CareAvailabilityResponse;
import com.PetCare.service.CareAvailability.CareAvailabilityService;
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
public class CareAvailabilityApiController {

    private final CareAvailabilityService careAvailabilityService;

    @Comment("돌봄 가능 일정 등록")
    @PostMapping("/{memberId}/careAvailability/new")
    public ResponseEntity<CareAvailability> saveCareAvailability(@PathVariable("memberId") long id,
                                                                 @RequestBody @Valid AddCareAvailabilityRequest request) {
        CareAvailability careAvailability = careAvailabilityService.save(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(careAvailability);
    }

    @Comment("특정 회원의 등록한 모든 돌봄 일정 조회")
    @GetMapping("/{memberId}/careAvailabilityList")
    public ResponseEntity<List<CareAvailabilityResponse>> findCareAvailabilityList(@PathVariable("memberId") long id) {
        List<CareAvailabilityResponse> careAvailabilityList = careAvailabilityService.findById(id);

        return ResponseEntity.ok()
                .body(careAvailabilityList);
    }

    @Comment("특정 회원의 등록한 특정 돌봄 일정 삭제")
    @DeleteMapping("/{memberId}/careAvailability/{careAvailabilityId}")
    public ResponseEntity<Void> deleteCareAvailability(@PathVariable("memberId") long id, @PathVariable("careAvailabilityId") long careAvailabilityId) {
        careAvailabilityService.delete(id, careAvailabilityId);

        return ResponseEntity.ok()
                .build();
    }

    @Comment("특정 회원의 등록한 특정 돌봄 일정 수정")
    @PutMapping("/{memberId}/careAvailability/{careAvailabilityId}")
    public ResponseEntity<CareAvailabilityResponse> updateCareAvailability(
            @PathVariable("memberId") long id, @PathVariable("careAvailabilityId") long careAvailabilityId,
            @RequestBody @Valid UpdateCareAvailabilityRequest request) {
        CareAvailabilityResponse updateCareAvailability = careAvailabilityService.update(id, careAvailabilityId, request);

        return ResponseEntity.ok()
                .body(updateCareAvailability);
    }

}
