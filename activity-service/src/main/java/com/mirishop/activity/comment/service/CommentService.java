package com.mirishop.activity.comment.service;

import com.mirishop.activity.comment.dto.CommentRequest;

public interface CommentService {

    Long createCommentOrReply(CommentRequest request, Long memberNumber, Long postId);

    void deleteComment(Long commentId, Long memberNumber);
}

