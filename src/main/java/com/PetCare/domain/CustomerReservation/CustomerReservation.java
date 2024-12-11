package com.PetCare.domain.CustomerReservation;

import com.PetCare.domain.Member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CustomerReservation { // 돌봄 예약(고객 시점)

    @Id
    @GeneratedValue
    @Column(name = "customer_reservation_id", updatable = false)
    private long id;

    @Comment("예약한 회원(고객) 번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}
