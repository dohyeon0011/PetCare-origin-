package com.PetCare.service.Reservation;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.domain.Review.Review;
import com.PetCare.dto.Reservation.ReservationResponse;
import com.PetCare.dto.Reservation.ReservationSitterResponse;
import com.PetCare.repository.CareAvailableDate.CareAvailableDateRepository;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Pet.PetRepository;
import com.PetCare.repository.Review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterReservationService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final CareAvailableDateRepository careAvailableDateRepository;
    private final ReviewRepository reviewRepository;

    @Comment("고객에게 돌봄 예약 가능한 돌봄사들의 정보 조회")
    @Transactional(readOnly = true)
    public List<ReservationSitterResponse.GetList> findReservableSitters() {
        List<CareAvailableDate> careAvailableDates = careAvailableDateRepository.findAll();

        Set<Member> sitters = careAvailableDates.stream()
                .map(careAvailableDate -> memberRepository.findById(careAvailableDate.getSitter().getId())
                        .orElseThrow(() -> new NoSuchElementException("돌봄 예약 가능 날짜를 등록하지 않은 돌봄사가 조회되어 실패했습니다.")))
                .collect(Collectors.toSet());

        return sitters.stream()
                .map(ReservationSitterResponse.GetList::new)
                .toList();
    }

    @Comment("돌봄 예약 가능 목록 중 선택한 돌봄사의 자세한 정보 조회")
    @Transactional(readOnly = true)
    public ReservationSitterResponse.GetDetail findReservableSitter(long sitterId) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄사 정보가 존재하지 않습니다."));

        List<CareAvailableDate> careAvailableDates = careAvailableDateRepository.findBySitterId(sitter.getId());
        if (careAvailableDates.isEmpty()) {
            throw new NoSuchElementException("해당 돌봄사는 돌봄 예약 가능한 날짜가 없습니다.");
        }

        List<Review> reviews = reviewRepository.findByCustomerReservationSitterId(sitter.getId());

        return new ReservationSitterResponse.GetDetail(sitter, reviews);
    }

    @Comment("고객이 예약할 때 보여줄 정보")
    @Transactional(readOnly = true)
    public ReservationResponse getReservationDetails(long customerId, long sitterId) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("현재 회원의 정보 조회에 실패했습니다."));

        verifyingPermissionsCustomer(customer);

        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄사 정보가 존재하지 않습니다."));

        verifyingPermissionsSitter(sitter);

        List<CareAvailableDate> careAvailableDates = careAvailableDateRepository.findBySitterId(sitter.getId());

        if (careAvailableDates.isEmpty()) {
            throw new NoSuchElementException("해당 돌봄사는 돌봄 예약 가능한 날짜가 없습니다.");
        }
        List<Pet> pets = petRepository.findByCustomerId(customer.getId());

        return new ReservationResponse(customer, sitter, careAvailableDates, pets);
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
