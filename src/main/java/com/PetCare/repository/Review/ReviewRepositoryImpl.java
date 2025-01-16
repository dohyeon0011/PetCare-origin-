package com.PetCare.repository.Review;

import com.PetCare.domain.Review.Review;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final EntityManager em;

    // 고객 시점 예약 엔티티의 sitterId를 기준으로 모든 리뷰 조회(+페이징)
    @Override
    public List<Review> findBySitterId(long sitterId, int page, int size) {
        return em.createQuery(
                        "select r from Review r " +
                                "where r.customerReservation.sitter.id = :sitterId", Review.class)
                .setParameter("sitterId", sitterId)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }
}
