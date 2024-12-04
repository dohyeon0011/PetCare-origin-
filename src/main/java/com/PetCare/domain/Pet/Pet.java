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
    @JoinColumn(name = "member_id")
    private Member member;

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
    private String profileImgUrl;

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
