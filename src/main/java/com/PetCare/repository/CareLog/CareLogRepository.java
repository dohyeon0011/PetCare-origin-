package com.PetCare.repository.CareLog;

import com.PetCare.domain.CareLog.CareLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {
}
