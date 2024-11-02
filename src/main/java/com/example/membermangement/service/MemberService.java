package com.example.membermangement.service;

import com.example.membermangement.model.Member;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;

public interface MemberService {
    public Member createMember(Member member);
    public Member getMemberById(Long id);
    public Member updateMember(Long id, Member memberDetails);
    public boolean deleteMember(Long id);
    public Page<Member> findMembers(String name, BigDecimal minBalance, BigDecimal maxBalance, Date birth_date, Integer age, Integer page, Integer size);
}
