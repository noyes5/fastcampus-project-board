package com.mirishop.userservice.user.dto;

import com.mirishop.userservice.user.entity.User;
import lombok.Getter;

@Getter
public class UserJoinResponse {

    private final Long number;
    private final String email;

    public UserJoinResponse(User user) {
        this.number = user.getUserId();
        this.email = user.getEmail();
    }
}
