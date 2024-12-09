package com.PetCare.service.Pet;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
import com.PetCare.dto.Pet.request.UpdatePetRequest;
import com.PetCare.dto.Pet.response.PetResponse;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
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
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<Pet> save(long memberId, List<AddPetRequest> request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("반려견 등록 오류: 현재 회원은 존재하지 않는 회원입니다."));

        verifyingPermissions(member);

        List<Pet> pets = new ArrayList<>();

        for (AddPetRequest addPetRequest : request) { // 각 PetDTO별 Member와 연관관계 편의 메서드 설정
            Pet pet = addPetRequest.toEntity();
            pet.addMember(member); // Member와 연관관계 설정
            pets.add(pet);
        }

        return petRepository.saveAll(pets);
    }

    @Comment("특정 회원의 반려견 조회")
    @Transactional(readOnly = true)
    public List<PetResponse> findById(long memberId){
        List<PetResponse> pets = petRepository.findByMemberId(memberId)
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());

        return pets;
    }

    @Comment("특정 회원의 특정 반려견 삭제")
    @Transactional
    public void delete(long memberId, long petId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        authorizetionMember(member);

        Pet pet = petRepository.findByMemberIdAndId(memberId, petId);

        // 여러 개의 엔티티를 한 번의 쿼리로 삭제하는 방식으로 성능을 개선
        // 이 방식은 영속성 컨텍스트(Persistence Context)에서 해당 엔티티들을 관리하지 않으므로, 엔티티 상태가 영속성 컨텍스트와 동기화되지 않음
        // 즉, 삭제 후 해당 엔티티는 더 이상 영속성 컨텍스트에서 관리되지 않으며, 이후에 해당 엔티티에 접근하려면 다시 조회해야 함
        petRepository.delete(pet);
//        petRepository.deleteAllInBatch(pets);
//        petRepository.deleteAll(pets); // 얘는 여러번 쿼리 나감
    }

    @Comment("특정 회원의 반려견 수정")
    @Transactional
    public List<PetResponse> update(long memberId, List<UpdatePetRequest> requests) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        List<Pet> pets = petRepository.findByMemberId(memberId);

        for (UpdatePetRequest request : requests) {
            Pet pet = pets.stream()
                    .filter(p -> p.getId() == request.getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("반려견 조회에 실패했습니다."));

            pet.update(
                    request.getName(), request.getAge(), request.getBreed(),
                    request.getMedicalConditions(), request.getProfileImgUrl()
            );

        }

        return pets.stream()
                .map(PetResponse::new)
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
        if (!member.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("고객만 반려견 등록 및 수정이 가능합니다.");
        }
    }

}
