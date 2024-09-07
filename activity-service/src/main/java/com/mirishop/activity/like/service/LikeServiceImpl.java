package com.mirishop.activity.like.service;

import com.mirishop.activity.client.NewsfeedFeignClient;
import com.mirishop.activity.client.UserFeignClient;
import com.mirishop.activity.client.dto.NewsFeedCreate;
import com.mirishop.activity.client.dto.NewsFeedDelete;
import com.mirishop.activity.comment.repository.CommentRepository;
import com.mirishop.activity.common.exception.CustomException;
import com.mirishop.activity.common.exception.ErrorCode;
import com.mirishop.activity.like.domain.LikeType;
import com.mirishop.activity.like.entity.Like;
import com.mirishop.activity.like.repository.LikeRepository;
import com.mirishop.activity.post.repository.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserFeignClient userFeignClient;
    private final NewsfeedFeignClient newsfeedFeignClient;

    /**
     * 유저 검증 후 postId를 받아서 좋아요를 생성하는 메소드
     */
    @Override
    @Transactional
    public void likePost(Long postId, Long currentMemberNumber) {
        userFeignClient.findMemberByNumber(currentMemberNumber);
        findPost(postId);

        if (isAlreadyPostLiked(postId, currentMemberNumber)) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }

        Like like = Like.builder()
                .memberNumber(currentMemberNumber)
                .likeType(LikeType.POST)
                .itemId(postId)
                .build();

        likeRepository.save(like);
        createNewsFeedForLike(like, postId);
    }

    /**
     * 유저 검증 후 postId를 받아서 좋아요를 삭제하는 메소드
     */
    @Override
    @Transactional
    public void unlikePost(Long postId, Long currentMemberNumber) {
        userFeignClient.findMemberByNumber(currentMemberNumber);
        findPost(postId);

        Optional<Like> likeOptional = likeRepository.findByItemIdAndLikeTypeAndMemberNumber(postId, LikeType.POST,
                currentMemberNumber);

        if (likeOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_LIKE);
        }

        Like like = likeOptional.get();
        likeRepository.delete(like);
        deleteNewsFeedForLike(like);
    }

    /**
     * 유저 검증 후 commentId를 받아서 좋아요를 생성하는 메소드
     */
    @Override
    @Transactional
    public void likeComment(Long commentId, Long currentMemberNumber) {
        userFeignClient.findMemberByNumber(currentMemberNumber);
        findComment(commentId);

        if (isAlreadyCommentLiked(commentId, currentMemberNumber)) {
            throw new CustomException(ErrorCode.ALREADY_LIKE);
        }

        Like like = Like.builder()
                .memberNumber(currentMemberNumber)
                .likeType(LikeType.COMMENT)
                .itemId(commentId)
                .build();

        likeRepository.save(like);
        createNewsFeedForLike(like, commentId);
    }

    /**
     * 유저 검증 후 commentId를 받아서 좋아요를 삭제하는 메소드
     */
    @Override
    @Transactional
    public void unlikeComment(Long commentId, Long currentMemberNumber) {
        userFeignClient.findMemberByNumber(currentMemberNumber);
        findComment(commentId);
        Optional<Like> likeOptional = likeRepository.findByItemIdAndLikeTypeAndMemberNumber(commentId, LikeType.COMMENT,
                currentMemberNumber);

        if (likeOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_LIKE);
        }
        Like like = likeOptional.get();
        likeRepository.delete(like);

        deleteNewsFeedForLike(like);
    }

    private void findPost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private boolean isAlreadyPostLiked(Long postId, Long currentMemberNumber) {
        return likeRepository.existsByItemIdAndLikeTypeAndMemberNumber(postId, LikeType.POST, currentMemberNumber);
    }

    private void findComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private boolean isAlreadyCommentLiked(Long commentId, Long currentMemberNumber) {
        return likeRepository.existsByItemIdAndLikeTypeAndMemberNumber(commentId, LikeType.COMMENT,
                currentMemberNumber);
    }

    private void createNewsFeedForLike(Like like, Long targetId) {
        NewsFeedCreate newsFeedCreate = NewsFeedCreate.builder()
                .memberNumber(like.getMemberNumber())
                .newsFeedType("LIKE")
                .activityId(like.getLikeId())
                .targetPostId(targetId)
                .createdAt(like.getCreatedAt())
                .isDeleted(false)
                .build();

        newsfeedFeignClient.createNewsFeed(newsFeedCreate);
    }

    private void deleteNewsFeedForLike(Like like) {
        Optional<Long> postIdOptional = findRelatedPostIdForLike(like);

        postIdOptional.ifPresent(postId -> {
            NewsFeedDelete newsFeedDelete = NewsFeedDelete.builder()
                    .newsFeedType("LIKE")
                    .activityId(like.getLikeId())
                    .isDeleted(true)
                    .build();

            newsfeedFeignClient.deleteNewsFeed(newsFeedDelete);
        });
    }

    private Optional<Long> findRelatedPostIdForLike(Like like) {
        if (like.getLikeType() == LikeType.POST) {
            return Optional.of(like.getItemId());
        } else if (like.getLikeType() == LikeType.COMMENT) {
            return findPostIdFromComment(like.getItemId());
        }
        return Optional.empty();
    }

    private Optional<Long> findPostIdFromComment(Long commentId) {
        return commentRepository.findPostIdByCommentId(commentId);
    }
}
