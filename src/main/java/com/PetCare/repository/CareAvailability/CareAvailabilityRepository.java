package com.PetCare.repository.CareAvailability;

import com.PetCare.domain.CareAvailability.CareAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareAvailabilityRepository extends JpaRepository<CareAvailability, Long> {

    // 특정 회원의 돌봄 가능한 날짜 모두 조회
    List<CareAvailability> findByMemberId(long memberId);

    // 특정 회원의 특정 돌봄 가능한 날짜 조회
    Optional<CareAvailability> findByMemberIdAndId(long memberId, long id);
}
