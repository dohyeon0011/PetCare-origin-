package com.PetCare.controller.Certification.view;

import com.PetCare.dto.Certification.response.CertificationResponse;
import com.PetCare.service.Certification.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets-care")
public class CertificationViewController {

    private final CertificationService certificationService;

    @Operation(description = "회원의 자격증 추가 || 수정")
    @GetMapping("/members/{sitterId}/certifications/new")
    public String newCertification(@PathVariable("sitterId") long sitterId, Model model) {
        List<CertificationResponse.GetList> certifications = certificationService.findById(sitterId);

        if (certifications.isEmpty()) {
            model.addAttribute("certifications", new CertificationResponse.GetList());
        } else {
            model.addAttribute("certifications", certifications);
        }

        return "certification/newCertification";
    }

    @Operation(description = "회원의 모든 자격증 조회")
    @GetMapping("/members/{sitterId}/certifications")
    public String getAllCertification(@PathVariable("sitterId") long sitterId, Model model) {
        List<CertificationResponse.GetList> certifications = certificationService.findById(sitterId);
        model.addAttribute("certifications", certifications);

        return "certification/certificationList";
    }
}
