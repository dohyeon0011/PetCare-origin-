package com.PetCare.domain.Review;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.dto.Review.response.ReviewResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_reservation_id")
    private CustomerReservation customerReservation;

    @Comment("해당 돌봄 예약에 회원이 남긴 평점")
    private Double rating;

    @Comment("해당 돌봄 예약에 회원이 남긴 리뷰")
    private String comment;

    @Comment("리뷰 작성일")
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Builder
    public Review(CustomerReservation customerReservation, Double rating, String comment) {
        addCustomerReservation(customerReservation);
        this.rating = rating;
        this.comment = comment;
    }

    @Comment("리뷰 수정")
    public void updateReview(Double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    // 고객 시점 예약 - 리뷰 연관관계 펀의 메서드
    public void addCustomerReservation(CustomerReservation customerReservation) {
        this.customerReservation = customerReservation;
        customerReservation.addReview(this);
    }

    public ReviewResponse.GetDetail toResponse() {
        return new ReviewResponse.GetDetail(this);
    }

}
