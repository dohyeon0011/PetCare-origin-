package com.PetCare.service.CareLog;

import com.PetCare.domain.CareLog.CareLog;
import com.PetCare.domain.Member.Member;
import com.PetCare.domain.Member.Role;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.CareLog.request.AddCareLogRequest;
import com.PetCare.dto.CareLog.request.UpdateCareLogRequest;
import com.PetCare.dto.CareLog.response.CareLogResponse;
import com.PetCare.repository.CareLog.CareLogRepository;
import com.PetCare.repository.Member.MemberRepository;
import com.PetCare.repository.Reservation.SitterSchedule.SitterScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CareLogService {

    private final MemberRepository memberRepository;
    private final SitterScheduleRepository sitterScheduleRepository;
    private final CareLogRepository careLogRepository;

    @Comment("케어 로그 작성")
    public CareLogResponse.GetDetail save(long sitterId, long sitterScheduleId, AddCareLogRequest request) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        authorizationMember(sitter);
        verifyingPermissionsCustomer(sitter);

        SitterSchedule sitterSchedule = sitterScheduleRepository.findById(sitterScheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 예약이 존재하지 않습니다."));

        CareLog careLog = request.toEntity(sitterSchedule);
        careLogRepository.save(careLog);

        return careLog.toResponse();
    }

    @Comment("돌봄사가 작성한 모든 돌봄 케어 로그 조회")
    @Transactional(readOnly = true)
    public List<CareLogResponse.GetList> findAll(long sitterId) {
        List<CareLog> careLogList = careLogRepository.findAllBySitterScheduleSitterId(sitterId);

        return careLogList.stream()
                .map(CareLogResponse.GetList::new)
                .toList();
    }

    @Comment("돌봄사가 특정 돌봄에 대해 작성한 돌봄 케어 로그 전체 조회")
    @Transactional(readOnly = true)
    public List<CareLogResponse.GetDetail> findAllById(long sitterId, long sitterScheduleId) {
        List<CareLog> careLogList = careLogRepository.findAllBySitterScheduleSitterIdAndSitterScheduleId(sitterId, sitterScheduleId);

        if (careLogList.isEmpty()) {
            throw new NoSuchElementException("해당 돌봄에 대해 작성된 케어 로그가 존재하지 않습니다.");
        }

        return careLogList
                .stream()
                .map(CareLogResponse.GetDetail::new)
                .toList();
    }

    @Comment("돌봄사가 특정 돌봄에 대해 작성한 특정 돌봄 케어 로그 단건 조회")
    @Transactional(readOnly = true)
    public CareLogResponse.GetDetail findById(long sitterId, long careLogId) {
        CareLog careLog = careLogRepository.findBySitterScheduleSitterIdAndId(sitterId, careLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 케어 로그 조회에 실패했습니다."));

        return careLog.toResponse();
    }

    @Comment("돌봄사의 특정 돌봄 케어 로그 삭제")
    public void delete(long sitterId, long careLogId) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        verifyingPermissionsCustomer(sitter);
        authorizationMember(sitter);

        CareLog careLog = careLogRepository.findBySitterScheduleSitterIdAndId(sitter.getId(), careLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 케어 로그가 존재하지 않습니다."));

        careLogRepository.delete(careLog);
    }

    @Comment("돌봄사의 특정 돌봄 케어 로그 수정")
    public CareLogResponse.GetDetail update(long sitterId, long careLogId, UpdateCareLogRequest request) {
        Member sitter = memberRepository.findById(sitterId)
                .orElseThrow(() -> new NoSuchElementException("로그인한 회원을 찾을 수 없습니다."));

        verifyingPermissionsCustomer(sitter);
        authorizationMember(sitter);

        CareLog careLog = careLogRepository.findBySitterScheduleSitterIdAndId(sitter.getId(), careLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 케어 로그가 존재하지 않습니다."));

        careLog.updateCareLog(request.getCareType(), request.getDescription(), request.getImgPath());

        return careLog.toResponse();
    }

    @Comment("돌봄 케어 로그 작성할 때 보여줄 정보")
    public CareLogResponse.GetReservation getReservation(long sitterId, long sitterScheduleId) {
        SitterSchedule sitterSchedule = sitterScheduleRepository.findById(sitterScheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 돌봄 예약을 조회하는데 실패했습니다."));

        return new CareLogResponse.GetReservation(sitterSchedule);
    }

    private static void authorizationMember(Member member) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인에 사용된 아이디 값 반환
//
//        if(!member.getLoginId().equals(userName)) {
//            throw new IllegalArgumentException("회원 본인만 가능합니다.");
//        }
    }

    public static void verifyingPermissionsCustomer(Member sitter) {
        if (!sitter.getRole().equals(Role.PET_SITTER)) {
            throw new IllegalArgumentException("돌봄사만 돌봄 케어 로그 작성,수정 및 삭제가 가능합니다.");
        }
    }
}
