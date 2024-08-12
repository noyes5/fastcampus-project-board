package com.mirishop.user.member.service;

import com.mirishop.user.user.auth.domain.UserDetailsImpl;
import com.mirishop.user.member.dto.ChangePasswordRequest;
import com.mirishop.user.member.dto.MemberJoinResponse;
import com.mirishop.user.member.dto.MemberRequest;
import com.mirishop.user.member.dto.MemberUpdateRequest;

public interface MemberService {

    MemberJoinResponse register(final MemberRequest memberRequest);

    void update(MemberUpdateRequest memberUpdateRequest, UserDetailsImpl userDetails);

    void changePassword(ChangePasswordRequest changePasswordRequest, UserDetailsImpl userDetails);
}
