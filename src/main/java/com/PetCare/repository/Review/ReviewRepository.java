package com.PetCare.repository.Review;

import com.PetCare.domain.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 고객 시점 예약 엔티티의 customerId를 기준으로 모든 리뷰 조회
    List<Review> findByCustomerReservationCustomerId(long customerId);

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 모든 리뷰 조회
    List<Review> findByCustomerReservationSitterId(long sitterId);

    // 고객 시점 예약 엔티티의 customerId를 기준으로 특정 리뷰 조회
    Optional<Review> findByCustomerReservationCustomerIdAndId(long customerId, long reviewId);

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 특정 리뷰 조회
    Optional<Review> findByCustomerReservationSitterIdAndId(long sitterId, long reviewId);
}
