package com.mirishop.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.mirishop.userservice.user.entity.User;
import com.mirishop.userservice.user.service.UserServiceImpl;
import com.mirishop.userservice.user.common.exception.ErrorCode;
import com.mirishop.userservice.user.common.exception.MemberException;
import com.mirishop.userservice.user.dto.UserJoinResponse;
import com.mirishop.userservice.user.dto.UserRequest;
import com.mirishop.userservice.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserServiceImpl memberService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상 가입 테스트")
    @Transactional
    void testRegisterUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .nickname("임상현")
                .email("graytree1400@gmail.com")
                .password("12345678")
                .profileImage(null)
                .bio("hello")
                .build();

        UserJoinResponse register = memberService.register(userRequest);
        User registeredUser = userRepository.findByEmail(register.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        assertThat(userRequest.getEmail()).isEqualTo(registeredUser.getEmail());
    }
}
