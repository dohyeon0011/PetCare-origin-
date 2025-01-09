package com.PetCare.repository.CareAvailableDate;

import com.PetCare.domain.CareAvailableDate.CareAvailableDate;
import com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CareAvailableDateRepository extends JpaRepository<CareAvailableDate, Long>, CareAvailableDateRepositoryCustom, QuerydslPredicateExecutor<CareAvailableDate> {

    // 특정 회원의 돌봄 가능한 날짜 모두 조회
    List<CareAvailableDate> findBySitterId(long sitterId);

    // 특정 회원의 특정 돌봄 가능한 날짜 엔티티로 조회
    Optional<CareAvailableDate> findBySitterIdAndId(long sitterId, long id);

    // 특정 회원의 특정 돌봄 가능한 날짜 DTO로 직접 조회
    @Query("SELECT new com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse$GetDetail(" +
            "c.id, c.availableAt, c.price, s.zipcode, s.address, c.status) " +
            "FROM CareAvailableDate c " +
            "JOIN c.sitter s " +
            "WHERE s.id = :sitterId AND c.id = :id")
    Optional<CareAvailableDateResponse.GetDetail> findBySitterIdAndIdDetail(@Param("sitterId") long sitterId, @Param("id") long id);

    // 특정 회원의 특정 날짜 조회
    Optional<CareAvailableDate> findBySitterIdAndAvailableAt(long sitterId, LocalDate availableAt);

    // 자신이 등록한 돌봄 가능 날짜 조회 페이징
//    @Query("SELECT new com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse$GetList(c) " +
//        "FROM CareAvailableDate c WHERE c.sitter.id = :sitterId")
    @Query("SELECT new com.PetCare.dto.CareAvailableDate.response.CareAvailableDateResponse$GetList(c.id, c.availableAt, c.price, c.status) " +
            "FROM CareAvailableDate c WHERE c.sitter.id = :sitterId")
    Page<CareAvailableDateResponse.GetList> findBySitterId(@Param("sitterId") long sitterId, Pageable pageable);

    // 돌봄 예약 가능 날짜를 등록한 회원 목록 조회
//    @Query("select distinct c.sitter from CareAvailableDate c")
//    Set<Member> findDistinctSitters();
}
