package com.PetCare.dto.CareAvailability.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCareAvailabilityRequest {

    @NotNull(message = "날짜 입력은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityAt;

    @NotNull(message = "돌봄 비용 입력은 필수입니다.")
    private int price;

}
