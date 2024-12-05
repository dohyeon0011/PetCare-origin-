package com.PetCare.dto.Certification.request;

import com.PetCare.domain.Certification.Certification;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddCertificationRequest {

    private String name;

    public Certification toEntity() {
        return Certification.builder()
                .name(name)
                .build();
    }

}
