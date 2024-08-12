package com.mirishop.user.auth.service;

import com.mirishop.user.auth.dto.LoginRequest;
import com.mirishop.user.auth.dto.TokenResponse;

public interface LoginService {

    TokenResponse login(final LoginRequest loginRequest);

    TokenResponse reissue(final String refreshToken);
}