package com.PetCare.dto.Member.response;

import com.PetCare.domain.Certification.Certification;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Member.SocialProvider;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Certification.response.CertificationResponse;
import com.PetCare.dto.Pet.response.PetResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MemberResponse {

    @NoArgsConstructor
    @Getter
    public static class GetCustomer {
        private long id;
        private String name;
        private String nickName;
        private String email;
        private String phoneNumber;
        private String address1;
        private String address2;
        private Role role;
        private SocialProvider socialProvider;
        private String introduction;
        private List<PetResponse> pets = new ArrayList<>();

        public GetCustomer(Member member, List<Pet> pets) {
            this.id = member.getId();
            this.name = member.getName();
            this.nickName = member.getNickName();
            this.email = member.getEmail();
            this.phoneNumber = member.getPhoneNumber();
            this.address1 = member.getAddress1();
            this.address2 = member.getAddress2();
            this.role = member.getRole();
            this.socialProvider = member.getSocialProvider();
            this.introduction = member.getIntroduction();
            this.pets = pets.stream()
                    .map(PetResponse::new)
                    .collect(Collectors.toList());
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetSitter {
        private long id;
        private String name;
        private String nickName;
        private String email;
        private String phoneNumber;
        private String address1;
        private String address2;
        private Role role;
        private SocialProvider socialProvider;
        private String introduction;
        private Integer careerYear;
        private List<CertificationResponse> certifications;

        public GetSitter(Member member, List<Certification> certifications) {
            this.id = member.getId();
            this.name = member.getName();
            this.nickName = member.getNickName();
            this.email = member.getEmail();
            this.phoneNumber = member.getPhoneNumber();
            this.address1 = member.getAddress1();
            this.address2 = member.getAddress2();
            this.role = member.getRole();
            this.socialProvider = member.getSocialProvider();
            this.introduction = member.getIntroduction();
            this.careerYear = member.getCareerYear();
            this.certifications = certifications.stream()
                    .map(CertificationResponse::new)
                    .collect(Collectors.toList());
        }
    }

}
