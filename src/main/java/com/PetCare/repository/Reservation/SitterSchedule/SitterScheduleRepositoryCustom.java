package com.PetCare.repository.Reservation.SitterSchedule;

import com.PetCare.dto.Reservation.SitterSchedule.response.SitterScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SitterScheduleRepositoryCustom {

    // 돌봄사에게 배정됐던 모든 예약 조회(+페이징)
    Page<SitterScheduleResponse.GetList> findBySitterId(long sitterId, Pageable pageable);
}
