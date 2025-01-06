package com.PetCare.repository.Reservation.CustomerReservation;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.dto.Reservation.CustomerReservation.response.CustomerReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Long> {

    // 특정 고객의 돌봄 예약 내역 전체 조회
    List<CustomerReservation> findByCustomerId(long customerId);

    // 고객의 번호로 특정 회원의 특정 돌봄 예약 내역 조회
    Optional<CustomerReservation> findByCustomerIdAndId(long customerId, long id);

    // 특정 고객의 돌봄 예약 내역 전체 조회(+페이징)
    @Query("SELECT new com.PetCare.dto.Reservation.CustomerReservation.response.CustomerReservationResponse$GetList(c) " +
            "FROM CustomerReservation c WHERE c.customer.id = :customerId")
    Page<CustomerReservationResponse.GetList> findByCustomerId(@Param("customerId") long customerId, Pageable pageable);

    // 돌봄사의 예약 번호로 특정 회원의 특정 돌봄 예약 내역 조회
//    Optional<CustomerReservation> findBySitterScheduleId(long sitterScheduleId);
}
