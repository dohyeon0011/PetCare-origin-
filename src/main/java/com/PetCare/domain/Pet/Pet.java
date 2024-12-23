package com.PetCare.domain.Pet;

import com.PetCare.domain.Member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pet {

    @Id @GeneratedValue
    @Column(name = "pet_id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Member customer;

    @Comment("반려견 이름")
    private String name;

    @Comment("반려견 나이")
    @Column(nullable = false)
    private int age;

    @Comment("반려견 품종")
    @Column(nullable = false)
    private String breed;

    @Comment("반려견 건강 상태 및 특이사항")
    private String medicalConditions;

    @Comment("반려견 프로필 사진")
    private String profileImgPath;

    // 고객-반려견 연관관계 편의 메서드
    public void addCustomer(Member customer) {
        this.customer = customer;
        customer.getPets().add(this);
    }

    @Builder
    public Pet(String name, int age, String breed, String medicalConditions, String profileImgPath) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.medicalConditions = medicalConditions;
        this.profileImgPath = profileImgPath;
    }

    public void update(String name, int age, String breed, String medicalConditions, String profileImgPath) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.medicalConditions = medicalConditions;
        this.profileImgPath = profileImgPath;
    }

}
