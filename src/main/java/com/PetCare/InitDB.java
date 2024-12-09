package com.PetCare;

import com.PetCare.domain.Certification.Certification;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Member.SocialProvider;
import com.PetCare.domain.Pet.Pet;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.builder()
                    .loginId("user1")
                    .password("aaw131aaw131")
                    .name("구창모")
                    .nickName("창모")
                    .email("changmo@naver.com")
                    .phoneNumber("010-1234-5678")
                    .address1("12345")
                    .address2("경기도")
                    .role(Role.valueOf("CUSTOMER"))
                    .socialProvider(null)
                    .introduction("귀여운 아이들 주인입니다.")
                    .careerYear(null)
                    .build();

            em.persist(member);

            Pet pet1 = Pet.builder()
                    .name("휴지")
                    .age(5)
                    .breed("비숑")
                    .medicalConditions("특정 사료만 먹음")
                    .profileImgUrl("https://cafe24.poxo.com/ec01/ejvpt/HOvhRhvOk+Cp2KY4JuusAg5T53na+Q88SEWskSb9Ko/8BAzWksUIq5Cxa0iMqHXD5IlcFun8qoZd+P4AUmvlyQ==/_/web/product/medium/202407/367bd9188f2f35d58c2c8be59c6196d5.jpg")
                    .build();

            Pet pet2 = Pet.builder()
                    .name("초코")
                    .age(2)
                    .breed("포메라니안")
                    .medicalConditions("간식 주면 안됨")
                    .profileImgUrl("https://cdn.crowdpic.net/detail-thumb/thumb_d_C1A78936BB1B43554DE572091820B23F.jpg")
                    .build();

            pet1.addMember(member);
            pet2.addMember(member);

            em.persist(pet1);
            em.persist(pet2);
        }

        public void dbInit2() {
            Member member = Member.builder()
                    .loginId("user2")
                    .password("blackrose12")
                    .name("윤진영")
                    .nickName("애쉬아일랜드")
                    .email("ashisland@naver.com")
                    .phoneNumber("010-1234-5678")
                    .address1("22222")
                    .address2("서울")
                    .role(Role.valueOf("PET_SITTER"))
                    .socialProvider(SocialProvider.valueOf("KAKAO"))
                    .introduction("섬세한 돌봄사 입니다.")
                    .careerYear(5)
                    .build();

            em.persist(member);

            Certification certification1 = Certification.builder()
                    .name("돌봄1급")
                    .build();

            Certification certification2 = Certification.builder()
                    .name("돌봄2급")
                    .build();

            certification1.addPetSitter(member);
            certification2.addPetSitter(member);

            em.persist(certification1);
            em.persist(certification2);
        }

        public void dbInit3() {
            Member member = Member.builder()
                    .loginId("user3")
                    .password("aaw131aaw131")
                    .name("김훈기")
                    .nickName("훈기")
                    .email("originalgimchi@naver.com")
                    .phoneNumber("010-1234-5678")
                    .address1("12345")
                    .address2("경기도")
                    .role(Role.valueOf("CUSTOMER"))
                    .socialProvider(null)
                    .introduction(null)
                    .careerYear(null)
                    .build();

            em.persist(member);
        }

        public void dbInit4() {
            Member member = Member.builder()
                    .loginId("user4")
                    .password("akffhs123!")
                    .name("Post Malone")
                    .nickName("포스트 말론")
                    .email("postmalone@gmail.com")
                    .phoneNumber("010-1234-5678")
                    .address1("22222")
                    .address2("서울")
                    .role(Role.valueOf("PET_SITTER"))
                    .socialProvider(SocialProvider.valueOf("NAVER"))
                    .introduction("POP STAR")
                    .careerYear(2)
                    .build();

            em.persist(member);
        }
    }
}
