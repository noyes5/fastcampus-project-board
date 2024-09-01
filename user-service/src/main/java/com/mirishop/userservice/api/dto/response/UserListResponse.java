package com.mirishop.userservice.api.dto.response;

import com.mirishop.userservice.domain.user.UserEntity;
import lombok.Getter;

@Getter
public class UserListResponse {

    private final String userId;
    private final String email;
    private final String nickname;
    private final String profileImage;

    public UserListResponse(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getEmail();
        this.profileImage = userEntity.getProfileImage();
    }
}
