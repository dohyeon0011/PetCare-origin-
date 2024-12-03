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
@RequestMapping("/api/members/customer")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<Member> addMember(@RequestBody @Valid AddMemberRequest request) {
        Member member = memberService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> findAllMember() {
        List<Member> members = memberService.findAll();

        return ResponseEntity.ok()
                .body(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findMember(@PathVariable long id) {
        Member member = memberService.findById(id);

        return ResponseEntity.ok()
                .body(member);
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
