package com.PetCare.dto.CareLog.request;

import com.PetCare.domain.CareLog.CareLog;
import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddCareLogRequest {

//    @NotBlank(message = "케어 로그를 작성할 돌봄을 선택해야 합니다.")
//    private long sitterScheduleId;

    private String careType;

    @NotBlank(message = "해당 돌봄 케어에 대한 상세 설명을 필수로 적어야 합니다.")
    private String description;

    private String imgPath;

    public CareLog toEntity(SitterSchedule sitterSchedule) {
        return CareLog.builder()
                .sitterSchedule(sitterSchedule)
                .careType(careType)
                .description(description)
                .imgPath(imgPath)
                .build();
    }

}
