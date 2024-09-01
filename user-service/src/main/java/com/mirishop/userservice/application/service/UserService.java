package com.mirishop.userservice.application.service;

import com.mirishop.userservice.api.dto.request.ChangePasswordRequest;
import com.mirishop.userservice.api.dto.request.UserJoinRequest;
import com.mirishop.userservice.api.dto.request.UserUpdateRequest;
import com.mirishop.userservice.api.dto.response.UserJoinResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserJoinResponse register(UserJoinRequest userJoinRequest, MultipartFile imageFile) throws IOException;

    void update(UserUpdateRequest userUpdateRequest, MultipartFile imageFile, String userId) throws IOException;

    void changePassword(ChangePasswordRequest changePasswordRequest, String userId);
}
