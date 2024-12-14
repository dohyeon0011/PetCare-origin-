package com.PetCare.service.CustomerReservation;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.PetReservation;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
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
        Member customer = memberRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 고객 정보 조회에 실패했습니다."));

        verifyingPermissions(customer);

        // 예약 배정되는 돌봄사 찾고 돌봄사가 등록했던 날짜중 선택된 날짜 찾아서 해당 날짜 예약 상태 바꾸기
        Member sitter = memberRepository.findById(request.getSitterId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 돌봄사 정보 조회에 실패했습니다."));

        // 예약으로 선택된 날짜 찾기
        CareAvailableDate careAvailableDate = memberRepository.findCareAvailableDatesByMemberIdAndAvailableAt(sitter.getId(), request.getReservationAt())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 돌봄사가 해당 날짜를 예약 가능 날짜로 등록하지 않았습니다."));

        List<PetReservation> petReservations = request.getPetIds().stream()
                .map(petId -> {
                    Pet pet = petRepository.findById(petId)
                            .orElseThrow(() -> new NoSuchElementException("반려견 정보 조회에 실패했습니다."));
                    return PetReservation.createPetReservation(pet);
                })
                .toList();

        CustomerReservation customerReservation = CustomerReservation.createCustomerReservation(customer, sitter, petReservations.toArray(new PetReservation[0]));
        customerReservation.changeReservationAt(request.getReservationAt());
        careAvailableDate.reservation();

        return customerReservationRepository.save(customerReservation);
    }

    @Comment("특정 회원의 예약 내역 전체 조회")
    @Transactional(readOnly = true)
    public List<CustomerReservationResponse.GetList> findAllById(long memberId) {
        Member customer = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("예약 조회 오류 : 현재 회원은 존재하지 않는 회원입니다."));

        List<CustomerReservationResponse.GetList> reservations = customerReservationRepository.findByCustomerId(customer.getId())
                .stream()
                .map(CustomerReservationResponse.GetList::new) // Constructor Reference 사용
                .collect(Collectors.toList());

        return reservations;
    }

    @Comment("특정 회원의 특정 예약 조회")
    @Transactional(readOnly = true)
    public CustomerReservationResponse.GetDetail findById(long memberId, long customerReservationId) {
        CustomerReservation customerReservation = customerReservationRepository.findByCustomerIdAndId(memberId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 정보가 존재하지 않습니다."));

        return customerReservation.toResponse();
    }

    @Comment("특정 회원의 특정 예약 취소")
    @Transactional
    public void delete(long memberId, long customerReservationId) {
        Member customer = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원 정보를 불러오는데 실패했습니다."));

        CustomerReservation customerReservation = customerReservationRepository.findByCustomerIdAndId(memberId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 정보가 존재하지 않습니다."));

        verifyingPermissions(customer);
        authorizetionMember(customer);

        CareAvailableDate careAvailableDate = memberRepository.findCareAvailableDatesByMemberIdAndAvailableAt(customerReservation.getSitter().getId(), customerReservation.getReservationAt())
                .orElseThrow(() -> new NoSuchElementException("예약 취소 오류: 해당 예약 날짜를 찾을 수 없습니다."));

        careAvailableDate.cancel();
        customerReservationRepository.delete(customerReservation);
    }

    private static void authorizetionMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    public static void verifyingPermissions(Member member) {
        if (!member.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("고객만 예약 등록 및 수정,삭제가 가능합니다.");
        }
    }

}
