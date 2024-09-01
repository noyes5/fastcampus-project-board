package com.mirishop.userservice.api.dto.request;

import com.mirishop.userservice.domain.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserJoinRequest {

    @NotNull(message = "Email은 null일 수 없습니다.")
    private String email;

    @NotNull(message = "Password는 null일 수 없습니다.")
    @Size(min = 8, message = "Password의 크기는 8글자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "Username은 null일 수 없습니다.")
    private String username;

    private String userId;

    @NotBlank
    private String bio;

    public UserEntity toEntity(String userId, String encodePassword, String profileImagePath) {
        return UserEntity.builder()
            .userId(userId)
            .email(this.email)
            .password(encodePassword)
            .username(this.username)
            .profileImage(profileImagePath)
            .bio(this.bio)
            .build();
    }
}
