package com.PetCare.service.Pet;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
import com.PetCare.dto.Pet.request.UpdatePetRequest;
import com.PetCare.dto.Pet.response.PetResponse;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
import lombok.RequiredArgsConstructor;
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
    public List<Pet> save(long id, List<AddPetRequest> request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("반려견 등록 오류: 현재 회원은 존재하지 않는 회원입니다."));

        List<Pet> pets = new ArrayList<>();

        for (AddPetRequest addPetRequest : request) { // 각 PetDTO별 Member와 연관관계 편의 메서드 설정
            Pet pet = addPetRequest.toEntity();
            pet.addMember(member); // Member와 연관관계 설정
            pets.add(pet);
        }
        return petRepository.saveAll(pets);
    }

    // 특정 회원의 반려견 조회
    @Transactional(readOnly = true)
    public List<PetResponse> findById(long id){
        List<PetResponse> pets = memberRepository.findPetsByMemberId(id)
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());

        if (pets.isEmpty()) {
            throw new NoSuchElementException("조회 오류: 등록된 반려견이 존재하지 않습니다.");
        }

        return pets;
    }

    @Transactional
    public void delete(long id, List<Long> petIds) {
        List<Pet> pets = petRepository.findByMemberIdAndIdIn(id, petIds);

        if (pets.isEmpty()) {
            throw new NoSuchElementException("삭제 오류: 등록된 반려견이 존재하지 않습니다.");
        }

        // 여러 개의 엔티티를 한 번의 쿼리로 삭제하는 방식으로 성능을 개선
        // 이 방식은 영속성 컨텍스트(Persistence Context)에서 해당 엔티티들을 관리하지 않으므로, 엔티티 상태가 영속성 컨텍스트와 동기화되지 않음
        // 즉, 삭제 후 해당 엔티티는 더 이상 영속성 컨텍스트에서 관리되지 않으며, 이후에 해당 엔티티에 접근하려면 다시 조회해야 함
        petRepository.deleteAllInBatch(pets);
//        petRepository.deleteAll(pets); // 얘는 여러번 쿼리 나감
    }

    @Transactional
    public List<PetResponse> update(long id, List<UpdatePetRequest> requests) {
        List<Pet> pets = memberRepository.findPetsByMemberId(id);

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

}
