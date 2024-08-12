package com.hh.mirishop.newsfeed.follow.entity;

import com.hh.mirishop.newsfeed.follow.domain.FollowId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "Follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Follow {

    @EmbeddedId
    private FollowId followId;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    public Follow(FollowId followId) {
        this.followId = followId;
    }
}
