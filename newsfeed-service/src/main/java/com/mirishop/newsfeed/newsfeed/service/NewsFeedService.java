package com.hh.mirishop.newsfeed.newsfeed.service;

import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedCreate;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedDelete;
import com.hh.mirishop.newsfeed.newsfeed.dto.NewsFeedUpdate;

public interface NewsFeedService {

    void createNewsFeed(NewsFeedCreate newsfeedCreate);

    void updateNewsFeed(NewsFeedUpdate newsFeedUpdate);

    void deleteNewsFeed(NewsFeedDelete newsFeedDelete);
}
