package com.mirishop.userservice.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.mirishop.userservice.domain.user.UserEntity;
import com.mirishop.userservice.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("유저 DB 저장 테스트")
class UserEntityRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserEntity createUser() {
        return UserEntity.builder()
                .email("noyes5@naver.com")
                .password("password")
                .nickname("임상현")
                .profileImage("profile.jpg")
                .bio("자기소개입니다.")
                .build();
    }

    @Test
    @DisplayName("정상 가입 테스트")
    @Transactional
    public void createUserTest() throws Exception {
        UserEntity userEntity = createUser();

        UserEntity findUserEntity = userRepository.save(userEntity);

        assertThat(findUserEntity).isEqualTo(userEntity);
    }
}