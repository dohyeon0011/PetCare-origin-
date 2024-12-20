package com.PetCare.dto.Review.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateReviewRequest {

    @NotNull(message = "평점을 0 ~ 5점 사이로 입력하세요.")
    @Min(value = 0, message = "평점은 최소 0점이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
    private Double rating;

    private String comment;
}
