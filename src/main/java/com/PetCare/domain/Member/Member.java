package com.PetCare.domain.Member;

import com.PetCare.domain.Pet.Pet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* 왜 엔티티에서만 @NoArgsConstructor가 필요한지 ??
    JPA는 엔티티를 관리하기 위해 리플렉션과 프록시 객체를 사용하며, 이 과정에서 기본 생성자가 필요함
    따라서 JPA 엔티티 클래스에만 @NoArgsConstructor를 적용하면 된다 (DTO에는 필요 X)
    DTO는 데이터 전송을 목적으로 하는 객체이므로 JPA와 같은 프레임워크가 직접 인스턴스화하지 않음
    DTO는 주로 클라이언트 요청 데이터를 매핑하거나, 엔티티 데이터를 반환하는 용도로 사용됨. 따라서 생성자는 명시적으로 작성하거나,
    Lombok의 @AllArgsConstructor 또는 빌더 패턴을 사용하는 경우가 많음
*/
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 생성하되, 외부에서 호출하지 못하도록 접근 제어를 protected로 제한(기본 객체 생성 막기), JPA는 리플렉션을 통해 생성자를 호출하므로 protected 접근제어도 문제없이 동작함
@Entity
@Table(name = "members")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id", updatable = false)
    private long id;

    // @Column에서 nullable은 데이터베이스 레벨에서 작동(DDL 생성 시 not null 제약 조건 추가, 데이터가 데이터 베이스에 직접 저장될 때 null 값이 들어오면 데이터베이스에서 오류 발생)
    @Comment("사용자가 로그인 할 아이디")
    @NotNull // 애플리케이션 레벨에서 작동(데이터를 데이터베이스에 저장하기 전 검사, 유효성 통과 못 하면 예외 터짐)
    @Column(nullable = false, unique = true, updatable = false)
    private String loginId;

    @Comment("비밀번호")
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Comment("사용자 실제 이름")
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Comment("사용자가 활동할 닉네임")
    @NotBlank
    @Column(nullable = false)
    private String nickName;

    @Email
    private String email;

    private String phoneNumber;

    @Comment("우편번호")
    @NotEmpty
    @Column(length = 5, nullable = false)
    private String address1;

    @Comment("상세주소")
    @NotEmpty
    @Column(nullable = false)
    private String address2;

    @Comment("회원 역할(고객, 돌봄사, 관리자)")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 사용자 가입 날짜 확인 : 사용자가 언제 가입했는지 추적할 수 있다
    // 리프레시 토큰 발급 시간 확인 : 리프레시 토큰이 언제 생성되었는지 확인하여 비정상적인 패턴(예: 짧은 시간 내 다중 발급)을 탐지할 수 있다
    // 이벤트 기반 처리 : 가입 후 X일 후 이메일 알림, 혜택 제공 등 타임라인 기반 기능에 사용된다
    @Comment("회원가입 날짜")
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 최근 로그인 시간 확인 : 소셜 로그인 사용자가 로그인할 때마다 정보를 갱신하면, 사용자의 마지막 활동을 추적할 수 있다
    // 리프레시 토큰 갱신 확인 : 리프레시 토큰을 재발급하면 해당 토큰의 updated_at을 갱신하여, 최근 발급 시점을 기록
    // 데이터 무결성 확인 : 예상치 못한 데이터 변경(예: 수동으로 수정된 경우)을 확인하거나, 기록으로 추적할 수 있다
    @Comment("최근 수정 날짜")
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Comment("소셜 로그인 제공자(KAKAO, NAVER, GOOGLE), 기본 값 null")
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @Comment("사용자 프로필 소개")
    private String introduction;

    @Comment("고객이 보유한 반려견 목록")
    @OneToMany(mappedBy = "member")
    @JsonIgnore // api 조회시 반려견 목록은 빠지고 조회됨
    private List<Pet> pets = new ArrayList<>();

    @Comment("돌봄사 경력 연차")
    private int careerYear;

    @Comment("돌봄사가 보유한 자격증")
    @Convert(converter = CertificateListConverter.class)
    @Column(columnDefinition = "TEXT") // 필요 시 길이를 늘림
    private List<String> certificates;

    @Builder
    public Member(String loginId, String password, String name, String nickName, String email, String phoneNumber, String address1, String address2, Role role, SocialProvider socialProvider, String introduction, int careerYear, List<String> certificates) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.role = role;
        this.socialProvider = socialProvider;
        this.introduction = introduction;
        this.careerYear = careerYear;
        this.certificates = certificates;
    }

    @Comment("회원정보 수정")
    public void update(String password, String name, String nickName, String email, String phoneNumber, String address1, String address2, String role, String introduction, int careerYear, List<String> certificates) {
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.role = Role.valueOf(role);
        this.introduction = introduction;
        this.careerYear = careerYear;
        this.certificates = certificates;
    }

}
