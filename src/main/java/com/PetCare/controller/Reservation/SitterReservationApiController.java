package com.PetCare.controller.Reservation;

import com.PetCare.dto.Reservation.ReservationResponse;
import com.PetCare.dto.Reservation.ReservationSitterResponse;
import com.PetCare.service.Reservation.SitterReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets-care")
public class SitterReservationApiController {

    private final SitterReservationService sitterReservationService;

    @Operation(description = "고객에게 돌봄 예약 가능한 돌봄사들의 정보 조회 API")
    @GetMapping("/reservableList")
    public ResponseEntity<List<ReservationSitterResponse.GetList>> findAllAvailableReservation() {
        List<ReservationSitterResponse.GetList> reservableSitters = sitterReservationService.findReservableSitters();

        return ResponseEntity.ok()
                .body(reservableSitters);
    }

    @Operation(description = "돌봄 예약 가능 목록 중 선택한 돌봄사의 자세한 정보 조회 API")
    @GetMapping("/reservable/members/{sitterId}")
    public ResponseEntity<ReservationSitterResponse.GetDetail> findReservationSitter(@PathVariable("sitterId") long sitterId) {
        ReservationSitterResponse.GetDetail reservableSitter = sitterReservationService.findReservableSitter(sitterId);

        return ResponseEntity.ok()
                .body(reservableSitter);
    }

    @Operation(description = "고객이 예약할 때 보여줄 정보 API")
    @GetMapping("/members/{customerId}/sitters/{sitterId}/reservations")
    public ResponseEntity<ReservationResponse> getReservationInfo(@PathVariable("customerId") long customerId, @PathVariable("sitterId") long sitterId) {
        ReservationResponse reservationDetails = sitterReservationService.getReservationDetails(customerId, sitterId);

        return ResponseEntity.ok()
                .body(reservationDetails);
    }

}
