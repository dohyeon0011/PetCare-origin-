package com.PetCare.controller;

import com.PetCare.domain.Member.Member;
import com.PetCare.dto.LoginRequest;
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

    /**
     * 장점: 코드가 간단함.
     * 단점: 키 값을 문자열로 가져와야 해서 타입 안정성이 부족함.
     */
    /*@PostMapping("/login")
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
    }*/

    /**
     * 장점: 코드 가독성이 좋고 타입 안정성이 높음
     * 단점: DTO 클래스를 따로 만들어야 함
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        String loginId = request.getLoginId();
        String password = request.getPassword();

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
