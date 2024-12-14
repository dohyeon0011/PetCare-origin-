package com.PetCare.repository.Member;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Pet.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    // 고객이 보유한 반려견 목록 전체 조회
    List<Pet> findPetsByMemberId(long memberId);

    // 돌봄사가 등록한 예약 가능 날짜 전체 조회
    public List<CareAvailableDate> findCareAvailableDatesByMemberId(long memberId);

    // 돌봄사가 등록한 예약 가능 날짜 중 특정 날짜 조회
    public Optional<CareAvailableDate> findCareAvailableDatesByMemberIdAndAvailableAt(long memberId, LocalDate availableDate);
}
