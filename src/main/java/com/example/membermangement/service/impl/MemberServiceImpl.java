package com.example.membermangement.service.impl;

import com.example.membermangement.dao.MemberRepository;
import com.example.membermangement.dto.MemberSpecifications;
import com.example.membermangement.model.Member;
import com.example.membermangement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 創建會員
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // 讀取單個會員
    public Member getMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        return memberOptional.orElse(null);
    }

    // 更新會員
    public Member updateMember(Long id, Member memberDetails) {
        Optional<Member> existingMemberOptional = memberRepository.findById(id);
        if (existingMemberOptional.isPresent()) {
            Member existingMember = existingMemberOptional.get();
            existingMember.setName(memberDetails.getName());
            existingMember.setBalance(memberDetails.getBalance());
            existingMember.setBirthDate(memberDetails.getBirthDate());
            existingMember.setAge(memberDetails.getAge());
            // 其他屬性更新...
            return memberRepository.save(existingMember);
        }
        return null;
    }

    // 刪除會員
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Page<Member> findMembers(
            String name,
            BigDecimal minBalance,
            BigDecimal maxBalance,
            Date birth_date,
            Integer age,
            Integer page,
            Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);

        // 創建 Specification
        Specification<Member> spec = Specification.where(MemberSpecifications.hasName(name))
                .and(MemberSpecifications.hasBalanceBetween(minBalance, maxBalance))
                .and(MemberSpecifications.hasBirthDate(birth_date))
                .and(MemberSpecifications.hasAge(age));

        // 返回查詢結果
        // 檢查所有條件是否為 null
        if (name == null && minBalance == null && maxBalance == null && birth_date == null && age == null) {
            return memberRepository.findAll(pageable); // 返回所有會員
        }

        return memberRepository.findAll(spec, pageable);
    }
}
