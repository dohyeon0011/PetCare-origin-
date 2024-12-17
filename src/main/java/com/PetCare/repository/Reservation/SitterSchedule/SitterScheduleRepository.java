package com.PetCare.repository.Reservation.SitterSchedule;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SitterScheduleRepository extends JpaRepository<SitterSchedule, Long> {

    // 돌봄사에게 배정됐던 특정 예약 조회
    Optional<SitterSchedule> findByCustomerReservation(CustomerReservation customerReservation);
}
