package com.mirishop.userservice.user.service;

import com.mirishop.userservice.auth.domain.UserDetailsImpl;
import com.mirishop.userservice.user.dto.ChangePasswordRequest;
import com.mirishop.userservice.user.dto.UserJoinResponse;
import com.mirishop.userservice.user.dto.UserRequest;
import com.mirishop.userservice.user.dto.UserUpdateRequest;

public interface UserService {

    UserJoinResponse register(final UserRequest userRequest);

    void update(UserUpdateRequest userUpdateRequest, UserDetailsImpl userDetails);

    void changePassword(ChangePasswordRequest changePasswordRequest, UserDetailsImpl userDetails);
}
