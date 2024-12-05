package com.PetCare.repository.Pet;

import com.PetCare.domain.Pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // 특정 회원이 보유중인 특정 반려견 목록만 조회
    public List<Pet> findByMemberIdAndIdIn(long memberId, List<Long> ids);
}
