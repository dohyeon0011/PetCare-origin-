package com.PetCare.repository.Reservation.SitterSchedule;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SitterScheduleRepository extends JpaRepository<SitterSchedule, Long> {

    // 돌봄사에게 배정됐던 모든 예약 조회
    List<SitterSchedule> findBySitterId(long sitterId);

    // 돌봄사의 특정 돌봄 예약 조회
    Optional<SitterSchedule> findBySitterIdAndId(long sitterId, long id);

    // 고객 예약으로 돌봄사에게 배정됐던 특정 예약 조회
    Optional<SitterSchedule> findByCustomerReservation(CustomerReservation customerReservation);
}
