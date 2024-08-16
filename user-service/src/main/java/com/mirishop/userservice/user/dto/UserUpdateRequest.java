package com.mirishop.userservice.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    private String nickname;
    private String profileImage;
    private String bio;
}
