package com.mirishop.user.member.service;

import com.mirishop.user.member.dto.MemberDetailResponse;
import com.mirishop.user.member.dto.MemberListResponse;

import java.util.List;

public interface MemberQueryService {

    boolean existsMemberByNumber(Long memberNumber);

    MemberDetailResponse getMemberDetail(Long memberNumber);

    List<MemberListResponse> listMembers();
}
