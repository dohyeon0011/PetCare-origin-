package com.PetCare.domain.Pet;

import com.PetCare.domain.Member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pet {

    @Id @GeneratedValue
    @Column(name = "pet_id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private String name; // 반려견 이름

    @NotBlank
    @Column(nullable = false)
    private int age; // 반려견 나이

    @NotBlank
    @Column(nullable = false)
    private String breed; // 반려견 품종

    private String medicalConditions; // 반려견 건강 상태 및 특이사항

    private String profileImgUrl; // 반려견 프로필 사진

    // 고객-반려견 연관관계 편의 메서드
    public void addMember(Member member) {
        this.member = member;
        member.getPets().add(this);
    }

    @Builder
    public Pet(String name, int age, String breed, String medicalConditions, String profileImgUrl) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.medicalConditions = medicalConditions;
        this.profileImgUrl = profileImgUrl;
    }

    public void update(String name, int age, String breed, String medicalConditions, String profileImgUrl) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.medicalConditions = medicalConditions;
        this.profileImgUrl = profileImgUrl;
    }

}
