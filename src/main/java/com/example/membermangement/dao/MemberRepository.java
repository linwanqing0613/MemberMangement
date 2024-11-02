package com.example.membermangement.dao;

import com.example.membermangement.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 分頁查詢所有會員
    @NonNull
    Page<Member> findAll(@NonNull Pageable pageable);
    // 分頁查詢特定會員
    Page<Member> findAll(Specification<Member> spec, Pageable pageable);
}