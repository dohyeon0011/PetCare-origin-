package com.PetCare.domain.Certification;

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
public class Certification {

    @Id
    @GeneratedValue
    @Column(name = "certification_id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("자격증 이름")
    private String name;

    @Builder
    public Certification(String name) {
        this.name = name;
    }

    // 돌봄사-자격증 연관관계 편의 메서드
    public void addPetSitter(Member member) {
        this.member = member;
        member.getCertifications().add(this);
    }

}
