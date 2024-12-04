package com.PetCare.repository.Member;

import com.PetCare.domain.Pet.Pet;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.PetCare.domain.Pet.QPet.pet;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Pet> findPetsByMemberId(long memberId) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.member.id.eq(memberId))
                .fetch();

    }

}
