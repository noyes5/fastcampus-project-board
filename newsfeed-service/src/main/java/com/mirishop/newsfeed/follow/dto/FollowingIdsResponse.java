package com.hh.mirishop.newsfeed.follow.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class FollowingIdsResponse {

    @Builder.Default
    private List<Long> followIds = new ArrayList<>();
}
