package com.PetCare.controller.Member.api;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.Member.request.AddMemberRequest;
import com.PetCare.dto.Member.request.UpdateMemberRequest;
import com.PetCare.service.Member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @Operation(description = "회원가입 API")
    @PostMapping("/new")
    public ResponseEntity<Member> saveMember(@RequestBody @Valid AddMemberRequest request) {
        Member member = memberService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(member);
    }

    @Operation(description = "전체 회원 조회 API")
    @GetMapping
    public ResponseEntity<List<?>> findAllMember() {
        return ResponseEntity.ok()
                .body(memberService.findAll());
    }

    @Operation(description = "특정 회원 조회 API")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> findMember(@PathVariable("memberId") long id) {
        Object member = memberService.findById(id);

        return ResponseEntity.ok()
                .body(member);
    }

    @Operation(description = "회원 탈퇴 API")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") long id) {
        memberService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(description = "회원 정보 수정 API")
    @PutMapping("/{memberId}")
    public ResponseEntity<Object> updateMember(@PathVariable("memberId") long id, @RequestBody @Valid UpdateMemberRequest request) {
        Object updateMember = memberService.update(id, request);

        return ResponseEntity.ok()
                .body(updateMember);
    }

}
