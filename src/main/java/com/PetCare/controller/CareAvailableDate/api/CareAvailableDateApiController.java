package com.PetCare.controller.CareAvailableDate.api;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.dto.CareAvailableDate.request.AddCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.request.UpdateCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import com.PetCare.service.CareAvailableDate.CareAvailableDateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class CareAvailableDateApiController {

    private final CareAvailableDateService careAvailableDateService;

    @Operation(description = "돌봄 가능 일정 등록 API")
    @PostMapping("/{sitterId}/careAvailableDate/new")
    public ResponseEntity<CareAvailableDate> saveCareAvailability(@PathVariable("sitterId") long id,
                                                                  @RequestBody @Valid AddCareAvailableDateRequest request) {
        CareAvailableDate careAvailableDate = careAvailableDateService.save(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(careAvailableDate);
    }

    @Operation(description = "모든 회원의 등록한 모든 돌봄 일정 조회 API")
    @GetMapping("/careAvailableDateList")
    public ResponseEntity<List<CareAvailableDateResponse.GetList>> findAllCareAvailableDate() {
        List<CareAvailableDateResponse.GetList> careAvailableDateAll = careAvailableDateService.findAll();

        return ResponseEntity.ok()
                .body(careAvailableDateAll);
    }

    @Operation(description = "회원의 등록한 모든 돌봄 일정 조회 API")
    @GetMapping("/{sitterId}/careAvailableDateList")
    public ResponseEntity<List<CareAvailableDateResponse.GetList>> findCareAvailableDateList(@PathVariable("sitterId") long id) {
        List<CareAvailableDateResponse.GetList> sitterAvailableDateList = careAvailableDateService.findAllById(id);

        return ResponseEntity.ok()
                .body(sitterAvailableDateList);
    }

    @Operation(description = "회원의 등록한 돌봄 일정 상세 조회 API")
    @GetMapping("/{sitterId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<CareAvailableDateResponse.GetList> findCareAvailableDateOne(@PathVariable("sitterId") long id,
                                                                             @PathVariable("careAvailableDateId") long careAvailableDateId) {
        CareAvailableDateResponse.GetList sitterAvailableDate = careAvailableDateService.findById(id, careAvailableDateId);

        return ResponseEntity.ok()
                .body(sitterAvailableDate);
    }

    @Operation(description = "회원의 등록한 특정 돌봄 일정 삭제 API")
    @DeleteMapping("/{sitterId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<Void> deleteCareAvailableDate(@PathVariable("sitterId") long id,
                                                       @PathVariable("careAvailableDateId") long careAvailableDateId) {
        careAvailableDateService.delete(id, careAvailableDateId);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(description = "회원의 등록한 특정 돌봄 일정 수정 API")
    @PutMapping("/{memberId}/careAvailableDate/{careAvailableDateId}")
    public ResponseEntity<CareAvailableDateResponse.GetList> updateCareAvailableDate(@PathVariable("memberId") long id,
                                                                            @PathVariable("careAvailableDateId") long careAvailableDateId,
                                                                            @RequestBody @Valid UpdateCareAvailableDateRequest request) {
        CareAvailableDateResponse.GetList updateSitterAvailableDate = careAvailableDateService.update(id, careAvailableDateId, request);

        return ResponseEntity.ok()
                .body(updateSitterAvailableDate);
    }

}
