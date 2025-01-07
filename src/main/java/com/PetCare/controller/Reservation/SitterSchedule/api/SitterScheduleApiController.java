package com.PetCare.controller.Reservation.SitterSchedule.api;

import com.PetCare.dto.Reservation.SitterSchedule.response.SitterScheduleResponse;
import com.PetCare.service.Reservation.SitterSchedule.SitterScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets-care")
public class SitterScheduleApiController {

    private final SitterScheduleService sitterScheduleService;

    @Operation(description = "돌봄사의 전체 돌봄 예약 목록 조회 API")
    @GetMapping("/members/{sitterId}/schedules")
    public ResponseEntity<Page<SitterScheduleResponse.GetList>> findAllSitterSchedule(@PathVariable("sitterId") long id, Pageable pageable) {
//        List<SitterScheduleResponse.GetList> sitterScheduleList = sitterScheduleService.findAllById(id);
        Page<SitterScheduleResponse.GetList> sitterScheduleList = sitterScheduleService.findAllById(id, pageable);

        return ResponseEntity.ok()
                .body(sitterScheduleList);
    }

    @Operation(description = "특정 돌봄사의 특정 돌봄 예약 정보 조회 API")
    @GetMapping("/members/{sitterId}/schedules/{sitterScheduleId}")
    public ResponseEntity<SitterScheduleResponse.GetDetail> findSitterSchedule(@PathVariable("sitterId") long id,
                                                                               @PathVariable("sitterScheduleId") long sitterScheduleId) {
        SitterScheduleResponse.GetDetail sitterSchedule = sitterScheduleService.findById(id, sitterScheduleId);

        return ResponseEntity.ok()
                .body(sitterSchedule);
    }

    @Operation(description = "특정 돌봄사의 특정 돌봄 예약 취소 API")
    @DeleteMapping("/members/{sitterId}/schedules/{sitterScheduleId}")
    public ResponseEntity<Void> deleteSitterSchedule(@PathVariable("sitterId") long id, @PathVariable("sitterScheduleId") long sitterScheduleId) {
        sitterScheduleService.delete(id, sitterScheduleId);

        return ResponseEntity.ok()
                .build();
    }

}
