package com.mirishop.userservice.api.controller;

import com.mirishop.userservice.api.dto.response.UserListResponse;
import com.mirishop.userservice.common.dto.BaseResponse;
import com.mirishop.userservice.api.dto.request.ChangePasswordRequest;
import com.mirishop.userservice.api.dto.request.UserJoinRequest;
import com.mirishop.userservice.api.dto.request.UserUpdateRequest;
import com.mirishop.userservice.api.dto.response.UserDetailResponse;
import com.mirishop.userservice.api.dto.response.UserJoinResponse;
import com.mirishop.userservice.application.service.UserQueryService;
import com.mirishop.userservice.application.service.UserService;
import com.mirishop.userservice.domain.user.UserEntity;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    private final UserService userService;
    private final UserQueryService userQueryService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's working in User Service")
            + ", port(local.server.port)=" + env.getProperty("local.server.port")
            + ", port(server.port)=" + env.getProperty("server.port");
    }

    /**
     * 회원가입 처리를 위한 엔드포인트
     *
     * @param userJoinRequest 회원가입 요청 정보
     * @param imageFile       프로필 이미지 파일 (선택적)
     * @return UserJoinResponse 회원가입 결과
     */
    @PostMapping("/users")
    public ResponseEntity<BaseResponse<UserJoinResponse>> register(@Valid @RequestBody UserJoinRequest userJoinRequest,
        @RequestPart(name = "profileImage", required = false) MultipartFile imageFile)
        throws IOException {
        UserJoinResponse registerUser = userService.register(userJoinRequest, imageFile);

        return ResponseEntity.ok(BaseResponse.of("회원 가입 성공", true, registerUser));
    }

    /**
     * 유저 정보 전체 조회용 엔드포인트
     *
     * @return List<UserListResponse> 회원가입 결과
     */
    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<UserListResponse>>> getUsers() {
        Iterable<UserEntity> userList = userQueryService.getUserByAll();

        List<UserListResponse> result = new ArrayList<>();
        userList.forEach(userEntity -> {
            result.add(new UserListResponse(userEntity));
        });

        return ResponseEntity.ok(BaseResponse.of("유저 정보를 불러왔습니다.", true, result));
    }

    /**
     * 유저 정보 조회를 위한 엔드포인트
     *
     * @param userId 조회할 유저의 ID
     * @return ResponseEntity<BaseResponse < UserDetailResponse>> 유저 상세 정보
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<BaseResponse<UserDetailResponse>> getUser(@PathVariable String userId) {
        UserDetailResponse userDetail = userQueryService.getUserDetail(userId);
        return ResponseEntity.ok(BaseResponse.of("유저 정보를 불러왔습니다.", true, userDetail));
    }

    /**
     * 유저 정보 수정을 위한 엔드포인트
     *
     * @param userUpdateRequest 수정 사항 정보
     * @param imageFile         프로필 이미지 파일 (선택적)
     * @param userId            헤더에서 꺼낸 유저 ID
     * @return 업데이트 성공 시 별도의 메시지를 보내지 않음
     */
    @PutMapping("/users")
    public ResponseEntity<BaseResponse<Void>> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
        @RequestPart(name = "profileImage", required = false) MultipartFile imageFile,
        @RequestHeader("X-USER-ID") String userId)
        throws IOException {
        userService.update(userUpdateRequest, imageFile, userId);
        return ResponseEntity.ok().body(BaseResponse.of("유저 정보 변경 완료", true, null));
    }

    /**
     * 유저 비밀번호 수정을 위한 엔드포인트
     *
     * @param changePasswordRequest 현재 비밀번호, 수정 비밀번호
     * @param userId                헤더에서 꺼낸 유저 ID
     * @return 업데이트 성공 시 별도의 메시지를 보내지 않음
     */
    @PutMapping("/users/password")
    public ResponseEntity<BaseResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
        @RequestHeader("X-USER-ID") String userId) {
        userService.changePassword(changePasswordRequest, userId);
        return ResponseEntity.ok().body(BaseResponse.of("패스워드 변경 완료", true, null));
    }
}
