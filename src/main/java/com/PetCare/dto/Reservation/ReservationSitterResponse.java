package com.PetCare.dto.Reservation;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.Certification.response.CertificationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReservationSitterResponse { // 고객이 예약하기 전 보여줄 정보

    @NoArgsConstructor
    @Getter
    public static class GetList { // 예약 가능 목록에 있는 돌봄사들의 정보
        private long sitterId;
        private String sitterName;
        private String introduction;

        public GetList(Member sitter) {
            this.sitterId = sitter.getId();
            this.sitterName = sitter.getName();
            this.introduction = sitter.getIntroduction();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail { // 예약 가능 목록 중 특정 돌봄사의 자세한 정보 + 해당 돌봄사의 적힌 리뷰도 보여줄 것
        private long sitterId;
        private String sitterName;
        private String introduction;
        private Integer careerYear;
        private List<CertificationResponse.GetReservation> certifications;
        private String zipcode;
        private String address;

        public GetDetail(Member sitter) {
            this.sitterId = sitter.getId();
            this.sitterName = sitter.getName();
            this.introduction = sitter.getIntroduction();
            this.careerYear = sitter.getCareerYear();
            this.certifications = sitter.getCertifications()
                    .stream()
                    .map(CertificationResponse.GetReservation::new)
                    .toList();
            this.zipcode = sitter.getZipcode();
            this.address = sitter.getAddress();
        }
    }

}
