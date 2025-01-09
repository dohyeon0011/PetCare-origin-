package com.PetCare.repository.Reservation.SitterSchedule;

import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.CareLog.response.CareLogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SitterScheduleRepository extends JpaRepository<SitterSchedule, Long>, SitterScheduleRepositoryCustom, QuerydslPredicateExecutor<SitterSchedule> {

    // 돌봄사에게 배정됐던 모든 예약 조회
    List<SitterSchedule> findBySitterId(long sitterId);

    // 돌봄사의 특정 돌봄 예약 조회
    Optional<SitterSchedule> findBySitterIdAndId(long sitterId, long id);

    // 고객 예약으로 돌봄사에게 배정됐던 특정 예약 조회
    Optional<SitterSchedule> findByCustomerReservation(CustomerReservation customerReservation);

    // 돌봄 케어 로그 작성할 때 보여주기 위한 정보
    @Query("SELECT new com.PetCare.dto.CareLog.response.CareLogResponse$GetNewCareLog(s.name, c.nickName) " +
            "FROM SitterSchedule ss " +
            "JOIN ss.sitter s " +
            "JOIN ss.customer c " +
            "WHERE ss.id = :sitterScheduleId")
    Optional<CareLogResponse.GetNewCareLog> findBySitterScheduleId(@Param("sitterScheduleId") long sitterScheduleId);
}
