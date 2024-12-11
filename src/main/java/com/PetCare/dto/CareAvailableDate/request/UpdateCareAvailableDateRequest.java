package com.PetCare.dto.CareAvailableDate.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCareAvailableDateRequest {

    @NotBlank(message = "날짜 입력은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityAt;

    @NotBlank(message = "돌봄 비용 입력은 필수입니다.")
    private int price;

}
