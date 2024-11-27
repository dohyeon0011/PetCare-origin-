package com.PetCare.dto.Member;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Member.SocialProvider;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* @NoArgsConstructor -> DTO에서 필요한가?
    DTO가 JSON 요청 데이터를 매핑할 때 Jackson이나 다른 직렬화/역직렬화 라이브러리가 기본 생성자가 필요
    예를 들어, 컨트롤러에서 요청 본문으로 들어온 JSON 데이터를 @RequestBody로 매핑할 경우 @NoArgsConstructor가 필요
 */
@NoArgsConstructor
@AllArgsConstructor // 테스트 코드용 -> 임시로 빠르게 데이터를 생성해내려고 하기 위해 쓰임
@Getter
@Builder
public class AddMemberRequest {

    @NotBlank(message = "아이디는 필수입니다.")
//    @Size(min = 5, max = 15, message = "아이디는 5자 이상, 15자 이하로 입력해야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수입니다.")
//    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 8, message = "닉네임은 2자 이상, 8자 이하로 입력해야 합니다.")
    private String nickName;

    @Email(message = "유효한 이메일을 입력해주세요.")
    private String email;

    private String phoneNumber;

    @NotEmpty(message = "우편번호는 필수입니다.")
    private String address1;

    @NotEmpty(message = "상세주소는 필수입니다.")
    private String address2;

    @NotNull
    private String role; // DTO에서는 문자열로 Role 값 받고 난 뒤에 enum 타입으로 변환

    private String socialProvider;

    private String introduction;

    // DTO에서 엔티티 객체로 변환하는 메서드
    public Member toEntity() {
        // socialProvider가 null이면 기본값을 설정
        SocialProvider provider = (socialProvider != null) ? SocialProvider.valueOf(socialProvider) : SocialProvider.DEFAULT;

        return Member.builder()
                .id(id)
                .password(password)
                .name(name)
                .nickName(nickName)
                .email(email)
                .phoneNumber(phoneNumber)
                .address1(address1)
                .address2(address2)
                .role(Role.valueOf(role))  // Role을 Enum으로 변환
                .socialProvider(provider)  // SocialProvider 처리
                .introduction(introduction)
                .build();
    }

}
