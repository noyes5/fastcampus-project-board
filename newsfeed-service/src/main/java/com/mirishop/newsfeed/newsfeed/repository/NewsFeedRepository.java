package com.hh.mirishop.newsfeed.newsfeed.repository;

import com.hh.mirishop.newsfeed.newsfeed.entity.NewsFeed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsFeedRepository extends MongoRepository<NewsFeed, Long> {


}