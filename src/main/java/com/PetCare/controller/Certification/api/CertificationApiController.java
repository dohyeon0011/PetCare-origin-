package com.PetCare.controller.Certification.api;

import com.PetCare.domain.Certification.Certification;
import com.PetCare.dto.Certification.request.AddCertificationRequest;
import com.PetCare.dto.Certification.request.UpdateCertificationRequest;
import com.PetCare.dto.Certification.response.CertificationResponse;
import com.PetCare.service.Certification.CertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class CertificationApiController {

    private final CertificationService certificationService;

    @Comment("특정 회원의 자격증 추가")
    @PostMapping("/{memberId}/certifications/new")
    public ResponseEntity<List<Certification>> addCertification(@PathVariable("memberId") long id, @RequestBody @Valid List<AddCertificationRequest> request) {
        List<Certification> certifications = certificationService.save(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(certifications);
    }

    @Comment("특정 회원의 모든 자격증 조회")
    @GetMapping("/{memberId}/certifications")
    public ResponseEntity<List<CertificationResponse>> findCertifications(@PathVariable("memberId") long id) {
        List<CertificationResponse> certifications = certificationService.findById(id);

        return ResponseEntity.ok()
                .body(certifications);
    }

    @Comment("특정 회원의 특정 자격증 삭제")
    @DeleteMapping("/{memberId}/certifications/{certificationId}")
    public ResponseEntity<Void> deleteCertification(@PathVariable("memberId") long id, @PathVariable("certificationId") long certificationId) {
        certificationService.delete(id, certificationId);

        return ResponseEntity.ok()
                .build();
    }

    @Comment("특정 회원의 자격증 정보 수정")
    @PutMapping("/{memberId}/certifications")
    public ResponseEntity<List<CertificationResponse>> updateCertification(@PathVariable("memberId") long id, @RequestBody @Valid List<UpdateCertificationRequest> requests) {
        List<CertificationResponse> updateCertifications = certificationService.update(id, requests);

        return ResponseEntity.ok()
                .body(updateCertifications);
    }

}
