package com.PetCare.service.Reservation.CustomerReservation;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.domain.Pet.PetReservation;
import com.PetCare.domain.Reservation.CustomerReservation.CustomerReservation;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.Reservation.CustomerReservation.request.AddCustomerReservationRequest;
import com.PetCare.dto.Reservation.CustomerReservation.response.CustomerReservationResponse;
import com.PetCare.repository.CareAvailableDate.CareAvailableDateRepository;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
import com.PetCare.repository.Reservation.CustomerReservation.CustomerReservationRepository;
import com.PetCare.repository.Reservation.SitterSchedule.SitterScheduleRepository;
import com.PetCare.service.Reservation.Reward.RewardServiceImpl;
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
    private final SitterScheduleRepository sitterScheduleRepository;
    private final CareAvailableDateRepository careAvailableDateRepository;
    private final RewardServiceImpl rewardService;

    @Transactional
    public CustomerReservation save(AddCustomerReservationRequest request) {
        Member customer = memberRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 고객 정보 조회에 실패했습니다."));

        verifyingPermissionsCustomer(customer);

        // 예약 배정되는 돌봄사 찾고 돌봄사가 등록했던 날짜중 선택된 날짜 찾아서 해당 날짜 예약 상태 바꾸기
        Member sitter = memberRepository.findById(request.getSitterId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 돌봄사 정보 조회에 실패했습니다."));

        verifyingPermissionsSitter(sitter);

        // 예약으로 선택된 날짜 찾기
        CareAvailableDate careAvailableDate = careAvailableDateRepository.findById(request.getCareAvailableId())
                .orElseThrow(() -> new NoSuchElementException("예약 오류: 돌봄사가 해당 날짜를 돌봄 가능 날짜로 등록하지 않았습니다."));

        // 돌봄에 배정될 반려견 중간 매핑 엔티티
        List<PetReservation> petReservations = request.getPetIds().stream()
                .map(petId -> {
                    Pet pet = petRepository.findByCustomerIdAndId(customer.getId(), petId)
                            .orElseThrow(() -> new NoSuchElementException("반려견 정보 조회에 실패했습니다."));
                    return PetReservation.createPetReservation(pet);
                })
                .toList();

        // 고객 시점 돌봄 예약 생성
        CustomerReservation customerReservation = CustomerReservation.createCustomerReservation(customer, sitter, careAvailableDate.getPrice(), petReservations.toArray(new PetReservation[0]));
        customerReservation.changeReservationAt(careAvailableDate.getAvailableAt());

        // 돌봄사 시점 돌봄 예약 생성
        SitterSchedule sitterReservation = SitterSchedule.createSitterReservation(customerReservation);
        sitterReservation.changeReservationAt(careAvailableDate.getAvailableAt());
        careAvailableDate.reservation();
        rewardService.addReward(customer.getId(), careAvailableDate.getPrice());

        return customerReservationRepository.save(customerReservation);
    }

    @Comment("특정 회원의 돌봄 예약 내역 전체 조회")
    @Transactional(readOnly = true)
    public List<CustomerReservationResponse.GetList> findAllById(long customerId) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("예약 조회 오류 : 현재 회원은 존재하지 않는 회원입니다."));

        List<CustomerReservationResponse.GetList> reservations = customerReservationRepository.findByCustomerId(customer.getId())
                .stream()
                .map(CustomerReservationResponse.GetList::new) // Constructor Reference 사용
                .collect(Collectors.toList());

        return reservations;
    }

    @Comment("특정 회원의 특정 돌봄 예약 조회")
    @Transactional(readOnly = true)
    public CustomerReservationResponse.GetDetail findById(long customerId, long customerReservationId) {
        CustomerReservation customerReservation = customerReservationRepository.findByCustomerIdAndId(customerId, customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 예약 내역이 존재하지 않습니다."));

        return customerReservation.toResponse();
    }

    @Comment("특정 회원의 특정 돌봄 예약 취소")
    @Transactional
    public void delete(long customerId, long customerReservationId) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원 정보를 불러오는데 실패했습니다."));

        CustomerReservation customerReservation = customerReservationRepository.findByCustomerIdAndId(customer.getId(), customerReservationId)
                .orElseThrow(() -> new NoSuchElementException("회원에게 해당 돌봄 예약 내역이 존재하지 않습니다."));

        authorizationMember(customer);
        verifyingPermissionsCustomer(customer);

        Member sitter = memberRepository.findById(customerReservation.getSitter().getId())
                .orElseThrow(() -> new NoSuchElementException("돌봄사의 정보를 조회하는데 실패했습니다."));

        verifyingPermissionsSitter(sitter);

        CareAvailableDate careAvailableDate = memberRepository.findCareAvailableDateBySitterIdAndAvailableAt(sitter.getId(), customerReservation.getReservationAt())
                .orElseThrow(() -> new NoSuchElementException("예약 취소 오류: 돌봄사에게 해당 예약 날짜를 찾을 수 없습니다."));

        SitterSchedule sitterSchedule = sitterScheduleRepository.findByCustomerReservation(customerReservation)
                .orElseThrow(() -> new NoSuchElementException("예약 취소 오류: 돌봄사에게 해당 예약이 존재하지 않습니다."));

        careAvailableDate.cancel();
        customerReservation.cancel();
        sitterSchedule.cancel();

//        customerReservationRepository.delete(customerReservation);
    }

    private static void authorizationMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    public static void verifyingPermissionsCustomer(Member customer) {
        if (!customer.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("고객만 예약 등록 및 수정,삭제가 가능합니다.");
        }
    }

    public static void verifyingPermissionsSitter(Member sitter) {
        if (!sitter.getRole().equals(Role.PET_SITTER)) {
            throw new IllegalArgumentException("돌봄 예약 배정 및 수정,삭제는 돌봄사만 가능합니다.");
        }
    }

}
