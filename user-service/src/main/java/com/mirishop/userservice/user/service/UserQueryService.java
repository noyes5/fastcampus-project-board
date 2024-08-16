package com.mirishop.userservice.user.service;

import com.mirishop.userservice.user.dto.UserDetailResponse;
import com.mirishop.userservice.user.dto.UserListResponse;

import java.util.List;

public interface UserQueryService {

    boolean existsMemberByNumber(Long memberNumber);

    UserDetailResponse getMemberDetail(Long memberNumber);

    List<UserListResponse> listMembers();
}
