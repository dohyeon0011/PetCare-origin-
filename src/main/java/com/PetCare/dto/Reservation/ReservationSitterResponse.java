package com.PetCare.dto.Reservation;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Review.Review;
import com.PetCare.dto.Certification.response.CertificationResponse;
import com.PetCare.dto.Review.response.ReviewResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReservationSitterResponse { // 고객이 예약하기 전 보여줄 정보

    @NoArgsConstructor
    @Getter
    public static class GetList { // 예약 가능 페이지에 있는 돌봄사들의 정보
        private long sitterId;
        private String sitterName;
        private String introduction;

        /*public GetList(Member sitter) {
            this.sitterId = sitter.getId();
            this.sitterName = sitter.getName();
            this.introduction = sitter.getIntroduction();
        }*/

        @QueryProjection
        public GetList(long sitterId, String sitterName, String introduction) {
            this.sitterId = sitterId;
            this.sitterName = sitterName;
            this.introduction = introduction;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail { // 예약 가능 페이지 목록 중 특정 돌봄사의 자세한 정보 + 해당 돌봄사에 대한 리뷰도 보여줄 것
        private long sitterId;
        private String sitterName;
        private String introduction;
        private Integer careerYear;
        private List<CertificationResponse.GetReservation> certifications;
        private String zipcode;
        private String address;
        private List<ReviewResponse.GetDetail> reviews;

        public GetDetail(Member sitter, List<Review> reviews) {
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
            /*this.reviews = reviews
                    .stream()
                    .map(ReviewResponse.GetDetail::new)
                    .toList();*/
            this.reviews = reviews
                    .stream()
                    .map(review -> {
                        return new ReviewResponse.GetDetail(review.getId(), review.getCustomerReservation().getId(), review.getCustomerReservation().getCustomer().getNickName(),
                                review.getCustomerReservation().getSitter().getName(), review.getRating(), review.getComment());
                    })
                    .toList();
        }
    }

}
