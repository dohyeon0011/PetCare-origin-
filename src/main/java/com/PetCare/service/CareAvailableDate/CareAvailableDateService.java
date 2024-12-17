package com.PetCare.service.CareAvailableDate;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.dto.CareAvailableDate.request.AddCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.request.UpdateCareAvailableDateRequest;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.CareAvailableDate.CareAvailableDateRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareAvailableDateService {

    private final MemberRepository memberRepository;
    private final CareAvailableDateRepository careAvailableDateRepository;

    @Transactional
    public CareAvailableDate save(long sitterId, AddCareAvailableDateRequest request) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("돌봄 날짜 등록 오류: 현재 회원은 존재하지 않는 회원입니다."));

        verifyingPermissions(sitter);

        CareAvailableDate careAvailableDate = request.toEntity();
        careAvailableDate.addPetSitter(sitter);

        return careAvailableDateRepository.save(careAvailableDate);
    }

    @Comment("모든 회원의 돌봄 가능 날짜 조회")
    @Transactional(readOnly = true)
    public List<CareAvailableDateResponse> findAll() {
        List<CareAvailableDateResponse> careAvailableDateList = careAvailableDateRepository.findAll()
                .stream()
                .map(CareAvailableDateResponse::new)
                .collect(Collectors.toList());

        return careAvailableDateList;
    }

    @Comment("등록한 돌봄 가능 날짜 조회")
    @Transactional(readOnly = true)
    public List<CareAvailableDateResponse> findAllById(long sitterId) {
        List<CareAvailableDateResponse> careAvailableDateList = careAvailableDateRepository.findBySitterId(sitterId)
                .stream()
                .map(CareAvailableDateResponse::new)
                .collect(Collectors.toList());

        return careAvailableDateList;
    }

    @Comment("등록한 돌봄 가능 날짜 단건 조회")
    @Transactional(readOnly = true)
    public CareAvailableDateResponse findById(long sitterId, long careAvailableDateId) {
        CareAvailableDate careAvailableDate = careAvailableDateRepository.findBySitterIdAndId(sitterId, careAvailableDateId)
                .orElseThrow(() -> new NoSuchElementException("등록한 돌봄 날짜가 존재하지 않습니다."));

        return careAvailableDate.toResponse();
    }

    @Comment("등록한 돌봄 가능 날짜 삭제")
    @Transactional
    public void delete(long memberId, long careAvailableDateId) {
        Member sitter = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(sitter);
        authorizetionMember(sitter);

        CareAvailableDate careAvailableDate = careAvailableDateRepository.findBySitterIdAndId(sitter.getId(), careAvailableDateId)
                .orElseThrow(() -> new NoSuchElementException("등록한 돌봄 날짜가 존재하지 않습니다."));

        careAvailableDateRepository.delete(careAvailableDate);
    }

    @Comment("등록한 돌봄 가능 정보 수정")
    @Transactional
    public CareAvailableDateResponse update(long sitterId, long careAvailableDateId, UpdateCareAvailableDateRequest request) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(sitter);
        authorizetionMember(sitter);

        CareAvailableDate careAvailableDate = careAvailableDateRepository.findBySitterIdAndId(sitter.getId(), careAvailableDateId)
                .orElseThrow(() -> new NoSuchElementException("등록한 돌봄 날짜가 존재하지 않습니다."));

        careAvailableDate.update(request.getAvailabilityAt(), request.getPrice());

        return careAvailableDate.toResponse();
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
