package com.PetCare.service.Review;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Review.Review;
import com.PetCare.dto.Review.request.AddReviewRequest;
import com.PetCare.dto.Review.request.UpdateReviewRequest;
import com.PetCare.dto.Review.response.ReviewResponse;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Reservation.CustomerReservation.CustomerReservationRepository;
import com.PetCare.repository.Review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final CustomerReservationRepository customerReservationRepository;
    private final ReviewRepository reviewRepository;

    @Comment("리뷰 작성")
    @Transactional
    public ReviewResponse.GetDetail save(long customerId, long reservationId, AddReviewRequest request) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        authorizationMember(customer);
        verifyingPermissionsCustomer(customer);

        CustomerReservation customerReservation = customerReservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("리뷰 등록 실패: 해당 예약을 찾을 수 없습니다."));

        if (reviewRepository.existsByCustomerReservation(customerReservation)) {
            throw new IllegalArgumentException("이미 해당 예약에 대한 리뷰가 존재합니다.");
        }

        Review review = request.toEntity(customerReservation);
        reviewRepository.save(review);

        return review.toResponse();
    }

    @Comment("특정 회원의 작성한 리뷰 전체 조회")
    @Transactional(readOnly = true)
    public List<ReviewResponse.GetList> findAllById(long customerId) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByCustomerReservationCustomerId(customer.getId());

        return reviews.stream()
                .map(ReviewResponse.GetList::new)
                .toList();
    }

    @Comment("특정 리뷰 조회")
    @Transactional(readOnly = true)
    public ReviewResponse.GetDetail findById(long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 건에 대한 리뷰를 찾을 수 없습니다."))
                .toResponse();
    }

    @Comment("특정 회원의 특정 리뷰 삭제")
    @Transactional
    public void delete(long customerId, long reviewId) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        authorizationMember(customer);
        verifyingPermissionsCustomer(customer);

        Review review = reviewRepository.findByCustomerReservationCustomerIdAndId(customer.getId(), reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }

    @Comment("특정 회원의 특정 리뷰 수정")
    @Transactional
    public ReviewResponse.GetDetail update(long customerId, long reviewId, UpdateReviewRequest request) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        authorizationMember(customer);
        verifyingPermissionsCustomer(customer);

        Review review = reviewRepository.findByCustomerReservationCustomerIdAndId(customer.getId(), reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다."));

        review.updateReview(request.getRating(), request.getComment());

        return review.toResponse();
    }

    private static void authorizationMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    public static void verifyingPermissionsCustomer(Member customer) {
        if (!customer.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("고객만 리뷰 작성,수정 및 삭제가 가능합니다.");
        }
    }

}
