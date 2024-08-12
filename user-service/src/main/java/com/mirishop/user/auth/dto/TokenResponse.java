package com.mirishop.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
