package com.mirishop.userservice.api.dto.response;

import com.mirishop.userservice.domain.user.Role;
import com.mirishop.userservice.domain.user.UserEntity;
import lombok.Getter;

@Getter
public class UserDetailResponse {

    private final String userId;
    private final String email;
    private final String username;
    private final String profileImage;
    private final String bio;
    private final Role role;

    public UserDetailResponse(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.username = userEntity.getUsername();
        this.profileImage = userEntity.getProfileImage();
        this.bio = userEntity.getBio();
        this.role = userEntity.getRole();
    }
}
