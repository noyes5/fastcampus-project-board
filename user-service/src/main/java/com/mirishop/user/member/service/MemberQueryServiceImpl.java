package com.mirishop.user.member.service;

import com.mirishop.user.common.exception.ErrorCode;
import com.mirishop.user.common.exception.MemberException;
import com.mirishop.user.member.dto.MemberDetailResponse;
import com.mirishop.user.member.dto.MemberListResponse;
import com.mirishop.user.member.entity.Member;
import com.mirishop.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    /**
     * 유저 정보를 받아 존재하는지 boolean을 리턴하는 내부용 메소드
     */
    @Override
    @Cacheable(value = "userCache", key = "#root.args[0]")
    @Transactional(readOnly = true)
    public boolean existsMemberByNumber(Long memberNumber) {
        return memberRepository.findByNumberAndIsDeletedFalse(memberNumber).isPresent();
    }

    /**
     * memberNumber를 받아 회원 상세 정보 조회하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public MemberDetailResponse getMemberDetail(Long memberNumber) {
        return memberRepository.findByNumberAndIsDeletedFalse(memberNumber)
                .map(MemberDetailResponse::new)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 전체 회원 목록을 조회하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberListResponse> listMembers() {
        List<Member> members = memberRepository.findAllByIsDeletedFalse();
        return members.stream()
                .map(MemberListResponse::new) // Member 엔티티를 리스트 DTO로 변환
                .toList();
    }
}
