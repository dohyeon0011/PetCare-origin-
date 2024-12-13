package com.PetCare.repository.CareAvailableDate;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CareAvailableDateRepository extends JpaRepository<CareAvailableDate, Long> {

    // 특정 회원의 돌봄 가능한 날짜 모두 조회
    List<CareAvailableDate> findByMemberId(long memberId);

    // 특정 회원의 특정 돌봄 가능한 날짜 조회
    Optional<CareAvailableDate> findByMemberIdAndId(long memberId, long id);

    // 특정 회원의 특정 날짜 조회
    Optional<CareAvailableDate> findByMemberIdAndAvailableAt(long memberId, LocalDate availableAt);
}
