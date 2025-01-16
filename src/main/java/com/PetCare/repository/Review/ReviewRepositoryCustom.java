package com.PetCare.repository.Review;

import com.PetCare.domain.Review.Review;

import java.util.List;

public interface ReviewRepositoryCustom {

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 모든 리뷰 조회(+페이징)
    List<Review> findBySitterId(long sitterId, int page, int size);
}
