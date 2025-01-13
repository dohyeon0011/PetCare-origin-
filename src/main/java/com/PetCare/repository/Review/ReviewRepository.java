package com.PetCare.repository.Review;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Review.Review;
import com.PetCare.dto.Review.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByCustomerReservation(CustomerReservation customerReservation);

    // 고객 시점 예약 엔티티의 customerId(고객)를 기준으로 모든 리뷰 조회
    List<Review> findByCustomerReservationCustomerId(long customerId);

    // 고객 시점 예약 엔티티의 customerId(고객)를 기준으로 모든 리뷰 조회(+페이징)
    @Query("SELECT new com.PetCare.dto.Review.response.ReviewResponse$GetList(r.id, c.nickName, s.name, r.rating) " +
            "FROM Review r " +
            "JOIN r.customerReservation cr " +
            "JOIN cr.customer c " +
            "JOIN cr.sitter s " +
            "WHERE c.id = :customerId")
    Page<ReviewResponse.GetList> findByCustomerReservationCustomerId(@Param("customerId") long customerId, Pageable pageable);

    // 고객 시점 예약 엔티티의 sitterId(돌봄사)를 기준으로 모든 리뷰 조회(+페이징)
    @Query("SELECT new com.PetCare.dto.Review.response.ReviewResponse$GetList(r.id, c.nickName, s.name, r.rating) " +
            "FROM Review r " +
            "JOIN r.customerReservation cr " +
            "JOIN cr.customer c " +
            "JOIN cr.sitter s " +
            "WHERE s.id = :sitterId")
    Page<ReviewResponse.GetList> findByCustomerReservationSitterId(@Param("sitterId") long sitterId, Pageable pageable);

    @Query("SELECT new com.PetCare.dto.Review.response.ReviewResponse$GetDetail(" +
            "r.id, cr.id, c.nickName, s.name, r.rating, r.comment) " +
            "FROM Review r " +
            "JOIN r.customerReservation cr " +
            "JOIN cr.customer c " +
            "JOIN cr.sitter s " +
            "WHERE r.id = :id")
    Optional<ReviewResponse.GetDetail> findReviewDetail(@Param("id") long id);

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 모든 리뷰 조회
    List<Review> findByCustomerReservationSitterId(long sitterId);

    // 고객 시점 예약 엔티티의 customerId를 기준으로 특정 리뷰 조회
    Optional<Review> findByCustomerReservationCustomerIdAndId(long customerId, long reviewId);

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 특정 리뷰 조회
    Optional<Review> findByCustomerReservationSitterIdAndId(long sitterId, long reviewId);
}
