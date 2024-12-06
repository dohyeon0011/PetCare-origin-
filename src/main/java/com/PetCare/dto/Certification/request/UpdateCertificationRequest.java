package com.PetCare.dto.Certification.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCertificationRequest {

    private long id;

    @NotBlank(message = "자격증 입력은 필수입니다.")
    private String name;

}
