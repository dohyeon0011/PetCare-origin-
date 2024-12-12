package com.PetCare.service.CustomerReservation;

import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.PetReservation;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.CustomerReservation.request.AddCustomerReservationRequest;
import com.PetCare.dto.CustomerReservation.response.CustomerReservationResponse;
import com.PetCare.repository.CustomerReservation.CustomerReservationRepository;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerReservationService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final CustomerReservationRepository customerReservationRepository;

    @Transactional
    public CustomerReservation save(AddCustomerReservationRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류 : 현재 회원은 존재하지 않는 회원입니다."));

        List<PetReservation> petReservations = request.getPetIds().stream()
                .map(petId -> {
                    Pet pet = petRepository.findById(petId)
                            .orElseThrow(() -> new NoSuchElementException("반려견 정보 조회에 실패했습니다."));
                    return PetReservation.createPetReservation(pet);
                })
                .collect(Collectors.toList());

        CustomerReservation customerReservation = CustomerReservation.createCustomerReservation(member, petReservations.toArray(new PetReservation[0]));
        customerReservation.addReservationAt(request.getReservationAt());

        return customerReservationRepository.save(customerReservation);
    }

    @Comment("예약 내역 조회")
    public List<CustomerReservationResponse> findAllById(long memberId) {
        List<CustomerReservationResponse> reservations = customerReservationRepository.findByMemberId(memberId)
                .stream()
                .map(CustomerReservationResponse::new)
                .collect(Collectors.toList());

        return reservations;
    }

    @Comment("특정 회원의 특정 예약 조회")
    public CustomerReservationResponse findById(long memberId, long customerReservationId) {
        CustomerReservation customerReservation = customerReservationRepository.findByMemberIdAndId(memberId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 정보가 존재하지 않습니다."));

        return customerReservation.toResponse();
    }

}
