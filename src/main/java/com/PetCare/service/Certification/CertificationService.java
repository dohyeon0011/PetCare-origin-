package com.PetCare.service.Certification;

import com.PetCare.domain.Certification.Certification;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.dto.Certification.request.AddCertificationRequest;
import com.PetCare.dto.Certification.request.UpdateCertificationRequest;
import com.PetCare.dto.Certification.response.CertificationResponse;
import com.PetCare.repository.Certification.CertificationRepository;
import com.PetCare.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<Certification> save(Long memberId, List<AddCertificationRequest> requests) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("자격증 등록 오류: 현재 회원은 존재하지 않는 회원입니다."));

        verifyingPermissions(member);

        List<Certification> certifications = new ArrayList<>();

        for (AddCertificationRequest request : requests) {
            Certification certification = request.toEntity();
            certification.addPetSitter(member);
            certifications.add(certification);
        }

        return certificationRepository.saveAll(certifications);
    }

    @Comment("특정 회원의 보유중인 자격증 조회")
    @Transactional(readOnly = true)
    public List<CertificationResponse> findById(long memberId) {
        List<CertificationResponse> certifications = certificationRepository.findByMemberId(memberId)
                .stream()
                .map(CertificationResponse::new)
                .collect(Collectors.toList());

        return certifications;
    }

    @Comment("특정 회원의 보유중인 특정 자격증 삭제")
    @Transactional
    public void delete(long memberId, long certificationId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        Certification certification = certificationRepository.findByMemberIdAndId(memberId, certificationId)
                .orElseThrow(() -> new NoSuchElementException("등록한 자격증이 존재하지 않습니다."));

        certificationRepository.delete(certification);
    }

    @Comment("특정 회원의 보유중인 자격증 수정")
    @Transactional
    public List<CertificationResponse> update(long memberId, List<UpdateCertificationRequest> requests) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        List<Certification> certifications = certificationRepository.findByMemberId(memberId);

        for (UpdateCertificationRequest request : requests) {
            Certification certification = certifications.stream()
                    .filter(c -> c.getId() == request.getId())
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("자격증 조회에 실패했습니다."));

            certification.update(request.getName());
        }

        return certifications.stream()
                .map(CertificationResponse::new)
                .collect(Collectors.toList());
    }

    private static void authorizetionMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    private static void verifyingPermissions(Member member) {
        if (!member.getRole().equals(Role.PET_SITTER)) {
            throw new IllegalArgumentException("돌봄사만 자격증 등록 및 수정,삭제가 가능합니다.");
        }
    }

}
