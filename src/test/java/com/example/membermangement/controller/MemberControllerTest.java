package com.example.membermangement.controller;

import com.example.membermangement.dao.MemberRepository;
import com.example.membermangement.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createMember() throws Exception {
        Member member = new Member("Alice", new BigDecimal("1000.50"), Date.valueOf("1990-01-01"), 34);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.balance").value(1000.50));
    }

    @Test
    void getMemberById() throws Exception {
        // 創建測試會員
        Member member = new Member("Alice", new BigDecimal("1000.50"), Date.valueOf("1990-01-01"), 34);
        Member savedMember = memberRepository.save(member);

        mockMvc.perform(get("/members/" + savedMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void updateMember() throws Exception {
        // 創建測試會員
        Member member = new Member("Alice", new BigDecimal("1000.50"), Date.valueOf("1990-01-01"), 34);
        Member savedMember = memberRepository.save(member);

        // 更新會員資料
        savedMember.setName("Alice Updated");
        savedMember.setBalance(new BigDecimal("1500.75"));

        mockMvc.perform(put("/members/" + savedMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Updated"))
                .andExpect(jsonPath("$.balance").value(1500.75));
    }

    @Test
    void deleteMember() throws Exception {
        // 創建測試會員
        Member member = new Member("Alice", new BigDecimal("1000.50"), Date.valueOf("1990-01-01"), 34);
        Member savedMember = memberRepository.save(member);

        mockMvc.perform(delete("/members/" + savedMember.getId()))
                .andExpect(status().isNoContent());

        // 驗證會員是否被刪除
        Optional<Member> deletedMember = memberRepository.findById(savedMember.getId());
        assert deletedMember.isEmpty();
    }

    @Test
    void searchMembers() throws Exception {
        // 創建一些測試會員
        memberRepository.save(new Member("Alice", new BigDecimal("1000.50"), Date.valueOf("1990-01-01"), 34));
        memberRepository.save(new Member("Bob", new BigDecimal("500.00"), Date.valueOf("1985-05-15"), 39));

        mockMvc.perform(get("/members/search")
                        .param("name", "Alice")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"));
    }
}