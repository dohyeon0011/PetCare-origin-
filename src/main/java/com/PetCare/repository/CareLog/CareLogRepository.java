package com.PetCare.repository.CareLog;

import com.PetCare.domain.CareLog.CareLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {

    // 돌봄사가 작성한 모든 돌봄 케어 로그 조회
    List<CareLog> findAllBySitterScheduleSitterId(long sitterId);

    // 돌봄사가 특정 돌봄에 대해 작성한 돌봄 케어 로그 조회
    List<CareLog> findAllBySitterScheduleSitterIdAndSitterScheduleId(long sitterId, long sitterScheduleId);

    // 돌봄사가 특정 돌봄에 대해 작성한 특정 돌봄 케어 로그 조회
    Optional<CareLog> findBySitterScheduleSitterIdAndId(long sitterId, long careLogId);
}
