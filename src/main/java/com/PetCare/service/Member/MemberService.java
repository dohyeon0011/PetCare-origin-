package com.PetCare.service.Member;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.Member.AddMemberRequest;
import com.PetCare.dto.Member.UpdateMemberRequest;
import com.PetCare.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(AddMemberRequest request) {
        validateDuplicateMember(request);

        return memberRepository.save(request.toEntity());
    }

    private void validateDuplicateMember(AddMemberRequest request) {
        List<Member> members = memberRepository.findByLoginId(request.getLoginId()).get();

        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public void delete(long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        authorizetionMember(member);
        memberRepository.delete(member);
    }

    @Transactional
    public Member update(long id, UpdateMemberRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        authorizetionMember(member);
        member.update(
                request.getPassword(), request.getName(), request.getNickName(), request.getEmail(),
                request.getPhoneNumber(), request.getAddress1(), request.getAddress2(),
                request.getIntroduction(), request.getRole(), request.getCareerYear(), request.getCertificates()
        );

        return member;
    }

    private static void authorizetionMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이다 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

}
