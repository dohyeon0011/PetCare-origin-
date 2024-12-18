package com.PetCare.service.Reservation.Reward;

import com.PetCare.domain.Member.Member;
import com.PetCare.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final MemberRepository memberRepository;

    @Override
    public void addReward(long customerId, double amount) {
        Member customer = memberRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("고객 정보 조회에 실패했습니다."));

        double reservationAmount = calculatorReward(amount);

        customer.addRewardPoints(reservationAmount);
    }

    @Override
    public double calculatorReward(double reservationAmount) { // 적립금 계산 로직 (예약금의 2% 적립)
        return reservationAmount * 0.02;
    }

}
