package com.PetCare.dto.Review.response;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Review.Review;
import com.querydsl.core.annotations.QueryProjection;
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

        @QueryProjection
        public GetList(long reviewId, String nickName, String name, Double rating) {
            this.id = reviewId;
            this.customerNickName = nickName;
            this.sitterName = name;
            this.rating = rating;
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

    @NoArgsConstructor
    @Getter
    public static class GetNewReview { // 리뷰 작성 시 보여질 폼 데이터
        private long customerReservationId;
        private String customerNickName;
        private String sitterName;
        private Double rating;
        private String comment;

        public GetNewReview(CustomerReservation customerReservation) {
            this.customerReservationId = customerReservation.getId();
            this.customerNickName = customerReservation.getCustomer().getNickName();
            this.sitterName = customerReservation.getSitter().getName();
        }
    }

}
