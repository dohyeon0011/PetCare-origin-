package com.petcare.dto.careavailabledate.request;

import com.petcare.domain.careAvailabledate.CareAvailableDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCareAvailableDateRequest {

    @NotNull(message = "날짜 입력은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // DTO는 뷰와 컨트롤러 간 데이터 처리에 집중
    private LocalDate availabilityAt;

    @NotBlank(message = "돌봄 비용 입력은 필수입니다.")
    private int price;

    public CareAvailableDate toEntity() {
        // 수동 타입 변환
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(availabilityAt, formatter);

        // <input type="date" th:field="*{date}" /> 타임리프 폼에서 이런 식으로 받으면 자동으로 스프링이 변환해줌
        return CareAvailableDate.builder()
                .availabilityAt(availabilityAt)
                .price(price)
                .build();
    }

}
