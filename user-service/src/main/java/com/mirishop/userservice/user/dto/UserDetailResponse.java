package com.mirishop.userservice.user.dto;

import com.mirishop.userservice.user.domain.Role;
import com.mirishop.userservice.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDetailResponse {

    private final Long number;
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final String bio;
    private final Role role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserDetailResponse(User user) {
        this.number = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.bio = user.getBio();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
