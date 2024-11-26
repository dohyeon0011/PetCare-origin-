package com.PetCare.domain.Member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_no", updatable = false)
    private long no;

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String nickName;

    @Email
    private String email;

    private String phoneNumber;

    @NotEmpty
    @Column(nullable = false)
    private String address1;

    @NotEmpty
    @Column(nullable = false)
    private String address2;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    private String introduction;

    @Builder
    public Member(String id, String password, String name, String nickName, String email, String phoneNumber, String address1, String address2, String introduction) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.introduction = introduction;
    }

    public void update(String password, String name, String nickName, String email, String phoneNumber, String address1, String address2, String introduction) {
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.introduction = introduction;
    }

}
