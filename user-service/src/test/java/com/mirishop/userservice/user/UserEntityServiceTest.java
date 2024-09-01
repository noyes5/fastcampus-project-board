package com.mirishop.userservice.user;

import com.mirishop.userservice.domain.user.UserRepository;
import com.mirishop.userservice.application.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserEntityServiceTest {

    @Autowired
    UserServiceImpl memberService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상 가입 테스트")
    @Transactional
    void testRegisterUser() throws Exception {
//        UserJoinRequest userJoinRequest = UserJoinRequest.()
//                .nickname("임상현")
//                .email("graytree1400@gmail.com")
//                .password("12345678")
//                .bio("hello")
//                .build();
//
//        UserJoinResponse register = memberService.register(userJoinRequest);
//        UserEntity registeredUserEntity = userRepository.findByEmail(register.getEmail())
//                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
//
//        assertThat(userJoinRequest.getEmail()).isEqualTo(registeredUserEntity.getEmail());
    }
}
