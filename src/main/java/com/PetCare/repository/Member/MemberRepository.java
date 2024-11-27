package com.PetCare.repository.Member;

import com.PetCare.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
