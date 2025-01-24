package com.PetCare.controller;

import com.PetCare.domain.Member.Member;
import com.PetCare.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class LoginController {

    private final UserService userService;

    /*@PostMapping("/login")
    public String login(@RequestParam String loginId, @RequestParam String password, HttpSession session, Model model) {
        Member member = userService.authenticate(loginId, password);

        if (member != null) {
            session.setAttribute("member", member);
            return "redirect:/pets-care/main";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }*/

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpSession session) {
        String loginId = request.get("loginId");
        String password = request.get("password");

        Member member = userService.authenticate(loginId, password);

        if (member != null) {
            session.setAttribute("member", member);

            return ResponseEntity.ok(Map.of("name", member.getName())); // 로그인 성공
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "아이디 또는 비밀번호가 잘못되었습니다.")); // 로그인 실패
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/pets-care/main";
    }

}
