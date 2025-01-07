package com.PetCare.repository.Reservation.SitterSchedule;

import com.PetCare.domain.Reservation.SitterSchedule.SitterSchedule;
import com.PetCare.dto.Reservation.SitterSchedule.response.QSitterScheduleResponse_GetList;
import com.PetCare.dto.Reservation.SitterSchedule.response.SitterScheduleResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.PetCare.domain.Reservation.SitterSchedule.QSitterSchedule.sitterSchedule;

@RequiredArgsConstructor
public class SitterScheduleRepositoryImpl implements SitterScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 돌봄사에게 배정됐던 모든 예약 조회(+페이징)
    @Override
    public Page<SitterScheduleResponse.GetList> findBySitterId(long sitterId, Pageable pageable) {
        // 컨텐트(데이터)만 가져오기
        List<SitterScheduleResponse.GetList> content = queryFactory
                .select(new QSitterScheduleResponse_GetList(
                        sitterSchedule.id, sitterSchedule.reservationAt, sitterSchedule.createdAt, sitterSchedule.status))
                .from(sitterSchedule)
                .where(sitterSchedule.sitter.id.eq(sitterId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 조회된 데이터가 페이지 항목 수보다 많을 때만 countQuery가 나감.(fetchCount() 호출)
        JPAQuery<SitterSchedule> countQuery = queryFactory
                .select(sitterSchedule)
                .from(sitterSchedule)
                .where(sitterSchedule.sitter.id.eq(sitterId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
