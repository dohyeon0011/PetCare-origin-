package com.PetCare.repository.Reservation.CustomerReservation;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Long> {

    // 특정 고객의 돌봄 예약 내역 전체 조회
    List<CustomerReservation> findByCustomerId(long customerId);

    // 고객의 번호로 특정 회원의 특정 돌봄 예약 내역 조회
    Optional<CustomerReservation> findByCustomerIdAndId(long customerId, long id);

    // 돌봄사의 예약 번호로 특정 회원의 특정 돌봄 예약 내역 조회
//    Optional<CustomerReservation> findBySitterScheduleId(long sitterScheduleId);
}
