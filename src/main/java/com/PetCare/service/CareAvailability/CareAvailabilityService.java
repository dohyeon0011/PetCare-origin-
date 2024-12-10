package com.PetCare.service.CareAvailability;

import com.PetCare.domain.CareAvailability.CareAvailability;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.dto.CareAvailability.request.AddCareAvailabilityRequest;
import com.PetCare.dto.CareAvailability.request.UpdateCareAvailabilityRequest;
import com.PetCare.dto.CareAvailability.response.CareAvailabilityResponse;
import com.PetCare.repository.CareAvailability.CareAvailabilityRepository;
import com.PetCare.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareAvailabilityService {

    private final MemberRepository memberRepository;
    private final CareAvailabilityRepository careAvailabilityRepository;

    @Transactional
    public CareAvailability save(long memberId, AddCareAvailabilityRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("돌봄 날짜 등록 오류: 현재 회원은 존재하지 않는 회원입니다."));

        verifyingPermissions(member);

        CareAvailability careAvailability = request.toEntity();
        careAvailability.addPetSitter(member);

        return careAvailabilityRepository.save(careAvailability);
    }

    @Comment("등록한 돌봄 가능 날짜 조회")
    @Transactional(readOnly = true)
    public List<CareAvailabilityResponse> findById(long memberId) {
        List<CareAvailabilityResponse> availabilityList = careAvailabilityRepository.findByMemberId(memberId)
                .stream()
                .map(CareAvailabilityResponse::new)
                .collect(Collectors.toList());

        return availabilityList;
    }

    @Comment("등록한 돌봄 가능 날짜 삭제")
    @Transactional
    public void delete(long memberId, long careAvailabilityId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        CareAvailability careAvailability = careAvailabilityRepository.findByMemberIdAndId(memberId, careAvailabilityId)
                .orElseThrow(() -> new NoSuchElementException("등록한 돌봄 날짜가 존재하지 않습니다."));

        careAvailabilityRepository.delete(careAvailability);
    }

    @Comment("등록한 돌봄 가능 정보 수정")
    @Transactional
    public CareAvailabilityResponse update(long memberId, long careAvailabilityId, UpdateCareAvailabilityRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        CareAvailability careAvailability = careAvailabilityRepository.findByMemberIdAndId(memberId, careAvailabilityId)
                .orElseThrow(() -> new NoSuchElementException("등록한 돌봄 날짜가 존재하지 않습니다."));

        careAvailability.update(request.getAvailabilityAt(), request.getPrice());

        return careAvailability.toResponse();
    }

    private static void authorizetionMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    public static void verifyingPermissions(Member member) {
        if (!member.getRole().equals(Role.PET_SITTER)) {
            throw new IllegalArgumentException("돌봄사만 돌봄 날짜 등록 및 수정,삭제가 가능합니다.");
        }
    }

}
