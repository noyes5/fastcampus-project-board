package com.mirishop.userservice.api.dto.response;

import com.mirishop.userservice.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserJoinResponse {

    private final String userId;
    private final String email;

    public static UserJoinResponse from(UserEntity user) {
        return UserJoinResponse.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .build();
    }
}
