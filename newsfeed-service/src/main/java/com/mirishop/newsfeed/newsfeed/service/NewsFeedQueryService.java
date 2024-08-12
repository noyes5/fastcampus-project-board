package com.hh.mirishop.newsfeed.newsfeed.service;

import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedResponse;
import org.springframework.data.domain.Page;

public interface NewsFeedQueryService {

    Page<NewsFeedResponse> getNewsfeedForMember(int page, int size, Long currentMemberNumber);
}
