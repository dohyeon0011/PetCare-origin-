package com.PetCare.dto.Review.response;

import com.PetCare.domain.Review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponse {

    @NoArgsConstructor
    @Getter
    public static class GetList { // 모든 리뷰 조회할 때
        private long id;
        private String customerNickName;
        private String sitterName;
        private Double rating;

        public GetList(Review review) {
            this.id = review.getId();
            this.customerNickName = review.getCustomerReservation().getCustomer().getNickName();
            this.sitterName = review.getCustomerReservation().getSitter().getName();
            this.rating = review.getRating();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail { // 리뷰의 자세한 정보 조회
        private long id;
        private long customerReservationId;
        private String customerNickName;
        private String sitterName;
        private Double rating;
        private String comment;

        public GetDetail(Review review) {
            this.id = review.getId();
            this.customerReservationId = review.getCustomerReservation().getId();
            this.customerNickName = review.getCustomerReservation().getCustomer().getNickName();
            this.sitterName = review.getCustomerReservation().getSitter().getName();
            this.rating = review.getRating();
            this.comment = review.getComment();
        }
    }

}
