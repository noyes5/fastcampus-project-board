package com.mirishop.newsfeed.follow.repository;

import com.mirishop.newsfeed.follow.domain.FollowId;
import com.mirishop.newsfeed.follow.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    // 특정 사용자가 팔로우하는 모든 사용자를 찾는 메소드
    @Query("SELECT f.followId.followingNumber FROM Follow f WHERE f.followId.followerNumber = :followerNumber")
    List<Long> findFollowingIdsByFollowerNumber(@Param("followerNumber") Long followerNumber);

    // 특정 사용자를 팔로우하는 모든 사용자를 찾는 메소드
    @Query("SELECT f FROM Follow f WHERE f.followId.followingNumber = :followingNumber")
    List<Follow> findAllByFollowingId(@Param("followingNumber") Long followingNumber);

    // 특정 팔로우 관계를 찾는 메소드 (복합 키 사용)
    Optional<Follow> findById(FollowId followId);

    // 팔로우 관계의 존재 여부를 확인하는 메소드
    boolean existsByFollowId(FollowId followId);
}
