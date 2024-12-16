package com.PetCare.repository.SitterSchedule;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.SitterSchedule.SitterSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SitterScheduleRepository extends JpaRepository<SitterSchedule, Long> {

    // 돌봄사에게 배정됐던 특정 예약 조회
    Optional<SitterSchedule> findByCustomerReservation(CustomerReservation customerReservation);
}
