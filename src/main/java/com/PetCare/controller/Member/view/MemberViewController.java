package com.PetCare.controller.Member.view;

import com.PetCare.dto.Member.response.MemberResponse;
import com.PetCare.service.Member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class MemberViewController {

    private final MemberService memberService;

    @Operation(description = "회원가입 || 회원 정보 수정")
    @GetMapping("/members/new")
    public String newMember(@RequestParam(name = "memberId", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("member", new MemberResponse());
        } else {
            Object member = memberService.findById(id);
            model.addAttribute("member", member);
        }

        return "member/newMember";
    }

    @Operation(description = "전체 회원 조회")
    @GetMapping("/members")
    public String getAllMember(Model model) {
        List<Object> members = memberService.findAll();
        model.addAttribute("members", members);

        return "admin/member/memberList";
    }

    @Operation(description = "특정 회원 조회")
    @GetMapping("/members/{memberId}")
    public String getMember(@PathVariable("memberId") Long id, Model model) {
        Object member = memberService.findById(id);
        model.addAttribute("member", member);

        return "member/memberDetail";
    }
}
