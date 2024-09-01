package com.mirishop.userservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @NotNull(message = "유저 이름은 null일 수 없습니다.")
    private String username;

    @NotNull(message = "프로필 image경로는 null일 수 없습니다.")
    private String profileImage;

    @NotBlank
    private String bio;
}
