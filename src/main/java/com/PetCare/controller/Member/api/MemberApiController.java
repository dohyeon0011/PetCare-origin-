package com.PetCare.controller.Member.api;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.Member.request.AddMemberRequest;
import com.PetCare.dto.Member.request.UpdateMemberRequest;
import com.PetCare.service.Member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/members/new")
    public ResponseEntity<Member> saveMember(@RequestBody @Valid AddMemberRequest request) {
        Member member = memberService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(member);
    }

    @GetMapping("/members")
    public ResponseEntity<List<?>> findAllMember() {
        return ResponseEntity.ok()
                .body(memberService.findAll());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?> findMember(@PathVariable long id) {
        Object member = memberService.findById(id);

        return ResponseEntity.ok()
                .body(member);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable long id) {
        memberService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Object> updateMember(@PathVariable long id, @RequestBody @Valid UpdateMemberRequest request) {
        Object updateMember = memberService.update(id, request);

        return ResponseEntity.ok()
                .body(updateMember);
    }

    // 돌봄사 회원 정보만 조회
    /*@GetMapping("/petsitters/{memberId}")
    public ResponseEntity<PetSitterResponse> findMember(@PathVariable("memberId") long id) {
        Member member = memberService.findById(id);

        return ResponseEntity.ok()
                .body(new PetSitterResponse(member));
    }

    // 회원 + 보유중인 반려견 목록 조회(고객)
    @GetMapping("/customers/{memberId}")
    public ResponseEntity<CustomerResponse> findPetsByMemberId(@PathVariable("memberId") long id) {
        Member member = memberService.findById(id);
        List<Pet> pets = memberService.findPetsByMemberId(id);

        return ResponseEntity.ok()
                .body(new CustomerResponse(member, pets));
    }*/
    
}
