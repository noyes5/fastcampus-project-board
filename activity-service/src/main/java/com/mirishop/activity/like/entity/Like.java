package com.mirishop.activity.like.entity;

import com.mirishop.activity.like.domain.LikeType;
import com.mirishop.activity.like.domain.LikeTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Likes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "item_id")
    private Long itemId; // 게시글 또는 댓글의 ID

    @Convert(converter = LikeTypeConverter.class)
    @Column(name = "like_type")
    private LikeType likeType;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
