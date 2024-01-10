package com.psychopath.dogstalking.follow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;

@Service
public class FollowServiceImpl {

    @Autowired
    private FollowSqlMapper followSqlMapper;

}
