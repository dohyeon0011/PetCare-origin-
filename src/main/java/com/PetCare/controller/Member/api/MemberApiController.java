package com.PetCare.controller.Member.api;

import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Pet.Pet;
import com.PetCare.dto.Member.request.AddMemberRequest;
import com.PetCare.dto.Member.request.UpdateMemberRequest;
import com.PetCare.dto.Member.response.CustomerResponse;
import com.PetCare.dto.Member.response.MemberResponse;
import com.PetCare.dto.Member.response.PetSitterResponse;
import com.PetCare.service.Member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<Member> addMember(@RequestBody @Valid AddMemberRequest request) {
        Member member = memberService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAllMember() {
        List<Member> members = memberService.findAll();
        List<MemberResponse> memberResponses = new ArrayList<>(); // 각 회원에 대해 MemberResponse 생성

        for (Member member : members) {
            List<Pet> pets = member.getPets(); // 해당 회원에 반려견이 있다면 반려견 목록을 가져오기

            if (!pets.isEmpty()) {
                memberResponses.add(new MemberResponse(member, pets));
            } else {
                // 반려견이 없는 경우에는 반려견 목록을 비워서 넣거나 null 처리
                memberResponses.add(new MemberResponse(member, new ArrayList<Pet>()));
            }
        }

        return ResponseEntity.ok()
                .body(memberResponses);
    }

    // 돌봄사 회원 정보만 조회
    @GetMapping("/{id}")
    public ResponseEntity<PetSitterResponse> findMember(@PathVariable long id) {
        Member member = memberService.findById(id);

        return ResponseEntity.ok()
                .body(new PetSitterResponse(member));
    }

    // 회원 + 보유중인 반려견 목록 조회(고객)
    @GetMapping("/{memberId}/pets")
    public ResponseEntity<CustomerResponse> findPetsByMemberId(@PathVariable("memberId") long id) {
        Member member = memberService.findById(id);
        List<Pet> pets = memberService.findPetsByMemberId(id);

        return ResponseEntity.ok()
                .body(new CustomerResponse(member, pets));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable long id) {
        memberService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable long id, @RequestBody @Valid UpdateMemberRequest request) {
        Member updateMember = memberService.update(id, request);

        return ResponseEntity.ok()
                .body(updateMember);
    }

}
