package com.PetCare.repository.CareAvailableDate;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CareAvailableDateRepository extends JpaRepository<CareAvailableDate, Long> {

    // 특정 회원의 돌봄 가능한 날짜 모두 조회
    List<CareAvailableDate> findBySitterId(long sitterId);

    // 특정 회원의 특정 돌봄 가능한 날짜 조회
    Optional<CareAvailableDate> findBySitterIdAndId(long sitterId, long id);

    // 특정 회원의 특정 날짜 조회
    Optional<CareAvailableDate> findBySitterIdAndAvailableAt(long sitterId, LocalDate availableAt);

    // 돌봄 예약 가능 날짜를 등록한 회원 목록 조회
    @Query("select distinct c.sitter from CareAvailableDate c")
    Set<Member> findDistinctSitters();
}
