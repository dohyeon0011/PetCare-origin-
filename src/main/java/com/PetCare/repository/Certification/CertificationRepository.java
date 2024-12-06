package com.PetCare.repository.Certification;

import com.PetCare.domain.Certification.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    // 특정 회원의 자격증 목록 조회
    List<Certification> findByMemberId(long memberId);

    Certification findByMemberIdAndId(long memberId, long id);

    // 특정 회원의 특정 자격증 목록 조회
    List<Certification> findByMemberIdAndIdIn(long memberId, List<Long> ids);
}
