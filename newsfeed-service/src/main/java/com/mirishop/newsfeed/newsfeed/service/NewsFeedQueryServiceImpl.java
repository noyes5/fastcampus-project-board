package com.hh.mirishop.newsfeed.newsfeed.service;

import com.hh.mirishop.newsfeed.follow.service.FollowService;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedResponse;
import com.hh.mirishop.newsfeed.newsfeed.entity.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedQueryServiceImpl implements NewsFeedQueryService {

    private final FollowService followService;
    private final MongoTemplate mongoTemplate;

    /**
     * 1. 팔로잉 유저 받아오고,
     * 2. 쿼리문에서 MemberNumber로 팔로잉 유저와 본인의 글을 불러오는 메소드
     *
     * page와 size는 controller에서 조절하고 최신 순으로 정렬
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewsFeedResponse> getNewsfeedForMember(int page, int size, Long currentMemberNumber) {
        List<Long> followings = followService.getFollowingIdsByMemberNumber(currentMemberNumber).getFollowIds();

        // MongoDB의 PageReqeust 생성함.
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 팔로우 유저와, 활동 쿼리를 생성함.
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("memberNumber").in(followings),
                Criteria.where("memberNumber").is(currentMemberNumber)
        );
        Query query = Query.query(criteria).with(pageRequest);

        long total = mongoTemplate.count(query, NewsFeed.class);

        List<NewsFeed> newsFeeds = mongoTemplate.find(query, NewsFeed.class);
        List<NewsFeedResponse> newsfeedResponses = newsFeeds.stream().map(NewsFeedResponse::fromNewsFeed).toList();

        return new PageImpl<>(newsfeedResponses, pageRequest, total);
    }
}
