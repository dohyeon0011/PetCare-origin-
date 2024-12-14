package com.PetCare.dto.Certification.response;

import com.PetCare.domain.Certification.Certification;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CertificationResponse {
    private long id;
    private String name;

    public CertificationResponse(Certification certification) {
        this.id = certification.getId();
        this.name = certification.getName();
    }

}
