package com.PetCare.repository.Member;

import com.PetCare.domain.Pet.Pet;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Pet> findPetsByMemberId(long memberId);
}
