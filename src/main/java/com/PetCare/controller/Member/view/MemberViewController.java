package com.PetCare.controller.Member.view;

import com.PetCare.dto.Member.response.MemberResponse;
import com.PetCare.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class MemberViewController {

    private final MemberService memberService;

    @Comment("회원가입")
    @GetMapping("/members/new")
    public String newMember(Model model) {
        model.addAttribute("member", new MemberResponse());

        return "member/newMember";
    }


    @Comment("전체 회원 조회")
    @GetMapping("/members")
    public String getAllMember(Model model) {
        List<Object> members = memberService.findAll();
        model.addAttribute("members", members);

        return "admin/member/memberList";
    }

    @Comment("특정 회원 조회")
    @GetMapping("/members/{memberId}")
    public String getMember(@PathVariable("memberId") Long id, Model model) {
        Object member = memberService.findById(id);
        model.addAttribute("member", member);

        return "member/memberDetail";
    }

    @Comment("회원 정보 수정")
    @GetMapping("/members/{memberId}/edit")
    public String editMember(@PathVariable("memberId") long id, Model model) {
        Object member = memberService.findById(id);
        model.addAttribute("member", member);

        return "member/editMember";
    }
}

