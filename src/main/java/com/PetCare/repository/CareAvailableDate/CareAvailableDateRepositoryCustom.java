package com.PetCare.repository.CareAvailableDate;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;

import java.util.List;
import java.util.Set;

public interface CareAvailableDateRepositoryCustom {

    // 돌봄 예약 가능 날짜를 등록한 회원 중 현재 예약이 가능한 회원만 조회
    Set<Member> findDistinctSitters();

    // 특정 돌봄사의 돌봄 예약 가능한 날짜만을 조회
    List<CareAvailableDate> findBySitterIdAndPossibility(long sitterId);
}
