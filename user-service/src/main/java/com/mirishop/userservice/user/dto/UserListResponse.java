package com.mirishop.userservice.user.dto;

import com.mirishop.userservice.user.entity.User;
import lombok.Getter;

@Getter
public class UserListResponse {

    private final Long number;
    private final String email;
    private final String nickname;
    private final String profileImage;

    public UserListResponse(User user) {
        this.number = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getEmail();
        this.profileImage = user.getProfileImage();
    }
}
