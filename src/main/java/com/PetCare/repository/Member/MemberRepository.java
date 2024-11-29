package com.PetCare.repository.Member;

import com.PetCare.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<List<Member>> findByLoginId(String loginId);
}
