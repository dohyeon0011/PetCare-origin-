package com.PetCare.repository.Member;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Member.SocialProvider;
import com.PetCare.dto.Member.AddMemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
// @ExtendWith(SpringExtension.class) Mock이나 Stub으로 의존성을 처리하고, Spring 컨텍스트를 로드하지 않는 테스트인 경우에 사용, SpringBootTest에 기본으로 달려 있음
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        //given: 테스트에 사용할 DTO 데이터 설정
        /*AddMemberRequest.builder()
                .id("user1")
                .password("aaw131")
                .name("구창모")
                .nickName("창모")
                .email("email@naver.com")
                .phoneNumber("010-1234-5678")
                .address1("123-456")
                .address2("경기도")
                .role("CUSTOMER")
                .socialProvider(null)
                .introduction("하이요")
                .build();*/
    }

    @DisplayName("회원가입_엔티티로 가입")
    @Test
    public void member_join() {
        //given
        Member member = new Member("user1", "aaw131", "구창모", "창모", "email@naver.com", "01012345678", "123-456", "경기도", Role.CUSTOMER, null, "하이요", 3, null);
        memberRepository.save(member);

        //when
        Optional<Member> findMember = memberRepository.findById(member.getNo());

        //then
        assertEquals(member, findMember.get()); // Optional에서 값 추출 후 비교
        Assertions.assertThat(member.getName()).isEqualTo("구창모");
    }

    @DisplayName("회원가입_DTO로 가입")
    @Test
    public void member_join_dto() {
        //given
        // DTO에 @Builder 쓰면 이렇게 하고 안 쓸거면 바로 아래처럼.
        AddMemberRequest savedMember = AddMemberRequest.builder()
                .id("user1")
                .password("aaw131")
                .name("구창모")
                .nickName("창모")
                .email("email@naver.com")
                .phoneNumber("010-1234-5678")
                .address1("12345")
                .address2("경기도")
                .role("CUSTOMER")
                .socialProvider(null)
                .introduction("하이요")
                .careerYear(3)
                .certificates(Collections.singletonList("돌봄1급, 돌봄2급"))
                .build();

        /*AddMemberRequest savedMember = new AddMemberRequest(
                "user1",
                "aaw131",
                "구창모",
                "창모",
                "email@naver.com",
                "010-1234-5678",
                "12345",
                "경기도",
                "CUSTOMER",
                null,
                "하이",
                3,
                null
        );*/

        //when
        Member member = savedMember.toEntity();
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getNo()).orElseThrow();
        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getNickName(), member.getNickName());
        assertThat(findMember.getEmail()).isEqualTo("email@naver.com");
        assertThat(findMember.getSocialProvider()).isEqualTo(SocialProvider.DEFAULT);
        assertThat(findMember.getCareerYear()).isEqualTo(3);
        assertThat(findMember.getCertificates()).containsExactly("돌봄1급, 돌봄2급");
    }

    @DisplayName("회원조회")
    @Test
    public void member_findAll() {
        //given
        AddMemberRequest savedMember = AddMemberRequest.builder()
                .id("user1")
                .password("aaw131")
                .name("구창모")
                .nickName("창모")
                .email("email@naver.com")
                .phoneNumber("010-1234-5678")
                .address1("123-456")
                .address2("경기도")
                .role("CUSTOMER")
                .socialProvider(null)
                .introduction("하이요")
                .build();
    }

    

}