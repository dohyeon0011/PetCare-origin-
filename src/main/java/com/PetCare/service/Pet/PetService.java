package com.PetCare.service.Pet;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Pet.request.AddPetRequest;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public List<Pet> save(long id, List<AddPetRequest> request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        List<Pet> pets = new ArrayList<>();

        for (AddPetRequest addPetRequest : request) { // 각 PetDTO별 Member와 연관관계 편의 메서드 설정
            Pet pet = addPetRequest.toEntity();
            pet.addMember(member); // Member와 연관관계 설정
            pets.add(pet);
        }
        return petRepository.saveAll(pets);
    }

}
