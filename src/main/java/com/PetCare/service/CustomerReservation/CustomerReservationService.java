package com.PetCare.service.CustomerReservation;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.CustomerReservation.CustomerReservation;
import com.PetCare.domain.CustomerReservation.PetReservation;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.CustomerReservation.request.AddCustomerReservationRequest;
import com.PetCare.dto.CustomerReservation.response.CustomerReservationResponse;
import com.PetCare.repository.CareAvailableDate.CareAvailableDateRepository;
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
    private final CareAvailableDateRepository careAvailableDateRepository;
    private final CustomerReservationRepository customerReservationRepository;

    @Transactional
    public CustomerReservation save(AddCustomerReservationRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 현재 회원은 존재하지 않는 회원입니다."));

        verifyingPermissions(member);

        List<CareAvailableDate> careAvailableDateList = careAvailableDateRepository.findAll();

        CareAvailableDate careAvailableDate = careAvailableDateList.stream()
                .filter(c -> c.getAvailableAt().equals(request.getReservationAt()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("요청하신 날짜는 예약이 불가능합니다."));

        List<PetReservation> petReservations = request.getPetIds().stream()
                .map(petId -> {
                    Pet pet = petRepository.findById(petId)
                            .orElseThrow(() -> new NoSuchElementException("반려견 정보 조회에 실패했습니다."));
                    return PetReservation.createPetReservation(pet);
                })
                .collect(Collectors.toList());

        CustomerReservation customerReservation = CustomerReservation.createCustomerReservation(member, petReservations.toArray(new PetReservation[0]));
        customerReservation.changeReservationAt(request.getReservationAt());
        careAvailableDate.reservation();

        return customerReservationRepository.save(customerReservation);
    }

    @Comment("특정 회원의 예약 내역 전체 조회")
    @Transactional(readOnly = true)
    public List<CustomerReservationResponse> findAllById(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("예약 조회 오류 : 현재 회원은 존재하지 않는 회원입니다."));

        List<CustomerReservationResponse> reservations = customerReservationRepository.findByMemberId(memberId)
                .stream()
                .map(reservation ->
                        new CustomerReservationResponse(member.getId(), member.getNickName(), reservation))
                .collect(Collectors.toList());

        return reservations;
    }

    @Comment("특정 회원의 특정 예약 조회")
    @Transactional(readOnly = true)
    public CustomerReservationResponse findById(long memberId, long customerReservationId) {
        CustomerReservation customerReservation = customerReservationRepository.findByMemberIdAndId(memberId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 정보가 존재하지 않습니다."));

        return customerReservation.toResponse();
    }

    @Comment("특정 회원의 특정 예약 취소")
    @Transactional
    public void delete(long memberId, long customerReservationId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 불러오는데 실패했습니다."));

        CustomerReservation customerReservation = customerReservationRepository.findByMemberIdAndId(memberId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약 정보가 존재하지 않습니다."));

        verifyingPermissions(member);
        authorizetionMember(member);

        // 돌봄사가 등록한 돌봄 가능 일정 중 일자가 일치한 것만을 불러와야 함.
        CareAvailableDate careAvailableDate = careAvailableDateRepository.findByMemberIdAndAvailableAt(customerReservation.getReservationAt())
                .orElseThrow(() -> new NoSuchElementException("해당 예약 날짜가 존재하지 않습니다."));

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
