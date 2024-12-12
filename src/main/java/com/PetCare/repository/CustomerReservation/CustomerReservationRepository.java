package com.PetCare.repository.CustomerReservation;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Long> {

    // 특정 회원의 예약 내역 전체 조회
    List<CustomerReservation> findByMemberId(long memberId);

    // 특정 회원의 특정 예약 내역 조회
    Optional<CustomerReservation> findByMemberIdAndId(long memberId, long id);
}
