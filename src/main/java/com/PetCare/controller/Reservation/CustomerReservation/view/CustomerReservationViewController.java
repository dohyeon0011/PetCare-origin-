package com.PetCare.controller.Reservation.CustomerReservation.view;

import com.PetCare.dto.Reservation.CustomerReservation.response.CustomerReservationResponse;
import com.PetCare.dto.Reservation.ReservationResponse;
import com.PetCare.service.Reservation.CustomerReservation.CustomerReservationService;
import com.PetCare.service.Reservation.SitterReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class CustomerReservationViewController {

    private final CustomerReservationService customerReservationService;
    private final SitterReservationService sitterReservationService;

    @Operation(description = "회원 돌봄 예약 생성")
    @GetMapping("/customers/{memberId}/sitters/{sitterId}/reservations/new")
    public String newReservation(@PathVariable("customerId") long customerId, @PathVariable("sitterId") long sitterId, Model model) {
        ReservationResponse reservationResponse = sitterReservationService.getReservationDetails(customerId, sitterId);
        model.addAttribute("reservationResponse", reservationResponse);

        return "reservation/newReservation";
    }

    @Operation(description = "회원의 모든 돌봄 예약 내역 조회")
    @GetMapping("/members/{customerId}/reservations")
    public String getAllReservation(@PathVariable("customerId") long customerId, Model model) {
        List<CustomerReservationResponse.GetList> reservations = customerReservationService.findAllById(customerId);
        model.addAttribute("reservations", reservations);

        return "reservation/reservationList";
    }

    @Operation(description = "회원의 특정 돌봄 예약 상세 조회")
    @GetMapping("/members/{customerId}/reservations/{customerReservationId}")
    public String getReservation(@PathVariable("customerId") long customerId,
                                 @PathVariable("customerReservationId") long customerReservationId, Model model) {
        CustomerReservationResponse.GetDetail reservation = customerReservationService.findById(customerId, customerReservationId);
        model.addAttribute("reservation", reservation);

        return "reservation/reservationDetail";
    }

}
