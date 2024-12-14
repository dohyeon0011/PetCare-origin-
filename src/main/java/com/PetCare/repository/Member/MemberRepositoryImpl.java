package com.PetCare.repository.Member;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Pet.Pet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.PetCare.domain.CareAvailableDate.QCareAvailableDate.careAvailableDate;
import static com.PetCare.domain.Pet.QPet.pet;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 고객이 보유한 반려견 목록 전체 조회
    @Override
    public List<Pet> findPetsByMemberId(long memberId) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.member.id.eq(memberId))
                .fetch();

    }

    // 돌봄사가 등록한 예약 가능 날짜 전체 조회
    public List<CareAvailableDate> findCareAvailableDatesByMemberId(long memberId) {
        LocalDateTime now = LocalDateTime.now();

        return queryFactory
                .selectFrom(careAvailableDate)
                .where(careAvailableDate.member.id.eq(memberId)
                        .and(careAvailableDate.availableAt.after(LocalDate.from(now))))
                .fetch();
    }

    // 돌봄사가 등록한 예약 가능 날짜 중 특정 날짜 조회
    public Optional<CareAvailableDate> findCareAvailableDatesByMemberIdAndAvailableAt(long memberId, LocalDate availableDate) {
        return Optional.ofNullable(queryFactory
                .selectFrom(careAvailableDate)
                .where(careAvailableDate.member.id.eq(memberId)
                        .and(careAvailableDate.availableAt.eq(availableDate)))
                .fetchOne());
    }
}
