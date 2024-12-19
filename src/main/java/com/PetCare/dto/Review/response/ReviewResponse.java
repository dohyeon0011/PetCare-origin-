package com.PetCare.dto.Review.response;

import com.PetCare.domain.Review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponse {

    @NoArgsConstructor
    @Getter
    public static class GetList {
        private long id;
        private int rating;

        public GetList(Review review) {
            this.id = review.getId();
            this.rating = review.getRating();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class GetDetail {
        private long id;
        private int rating;
        private String comment;

        public GetDetail(Review review) {
            this.id = review.getId();
            this.rating = review.getRating();
            this.comment = review.getComment();
        }
    }

}
