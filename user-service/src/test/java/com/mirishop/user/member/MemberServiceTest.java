package com.mirishop.user.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.mirishop.user.member.entity.Member;
import com.mirishop.user.member.service.MemberServiceImpl;
import com.mirishop.user.user.common.exception.ErrorCode;
import com.mirishop.user.user.common.exception.MemberException;
import com.mirishop.user.member.dto.MemberJoinResponse;
import com.mirishop.user.member.dto.MemberRequest;
import com.mirishop.user.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("정상 가입 테스트")
    @Transactional
    void testRegisterUser() throws Exception {
        MemberRequest memberRequest = MemberRequest.builder()
                .nickname("임상현")
                .email("graytree1400@gmail.com")
                .password("12345678")
                .profileImage(null)
                .bio("hello")
                .build();

        MemberJoinResponse register = memberService.register(memberRequest);
        Member registeredMember = memberRepository.findByEmail(register.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        assertThat(memberRequest.getEmail()).isEqualTo(registeredMember.getEmail());
    }
}
