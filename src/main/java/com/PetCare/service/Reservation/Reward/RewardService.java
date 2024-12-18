package com.PetCare.service.Reservation.Reward;

public interface RewardService {
    void addReward(long customerId, double amount); // 적립 메서드
    double calculatorReward(double reservationAmount); // 적립금 계산 메서드
}
