package com.PetCare.dto.Certification.response;

import com.PetCare.domain.Certification.Certification;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CertificationResponse {
    private String name;

    public CertificationResponse(Certification certification) {
        this.name = certification.getName();
    }

}
