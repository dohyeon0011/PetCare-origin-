package com.PetCare.controller.Reservation.CustomerReservation.api;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.dto.Reservation.request.AddCustomerReservationRequest;
import com.PetCare.dto.Reservation.response.ReservationResponse;
import com.PetCare.service.Reservation.CustomerReservation.CustomerReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/PetsCare")
public class CustomerReservationApiController {

    private final CustomerReservationService customerReservationService;

    @Operation(description = "회원 돌봄 예약 생성 API")
    @PostMapping("/reservation/new")
    public ResponseEntity<CustomerReservation> saveCustomerReservation(@RequestBody @Valid AddCustomerReservationRequest request) {
        CustomerReservation customerReservation = customerReservationService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerReservation);
    }

    @Operation(description = "회원의 모든 돌봄 예약 내역 조회 API")
    @GetMapping("/{memberId}/reservationList")
    public ResponseEntity<List<ReservationResponse.GetList>> findCustomerReservationList(@PathVariable("memberId") long id) {
        List<ReservationResponse.GetList> customerReservationList = customerReservationService.findAllById(id);

        return ResponseEntity.ok()
                .body(customerReservationList);
    }

    @Operation(description = "회원의 특정 돌봄 예약 상세 조회 API")
    @GetMapping("/{memberId}/reservation/{customerReservationId}")
    public ResponseEntity<ReservationResponse.GetDetail> findCustomerReservationOne(@PathVariable("memberId") long id,
                                                                                    @PathVariable("customerReservationId") long customerReservationId) {
        ReservationResponse.GetDetail customerReservation = customerReservationService.findById(id, customerReservationId);

        return ResponseEntity.ok()
                .body(customerReservation);
    }

    @Operation(description = "특정 회원의 특정 돌봄 예약 취소 API")
    @DeleteMapping("/{memberId}/reservation/{customerReservationId}")
    public ResponseEntity<Void> deleteCustomerReservation(@PathVariable("memberId") long id,
                                                          @PathVariable("customerReservationId") long customerReservationId) {
        customerReservationService.delete(id, customerReservationId);

        return ResponseEntity.ok()
                .build();
    }

}
