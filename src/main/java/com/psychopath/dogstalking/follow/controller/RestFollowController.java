package com.psychopath.dogstalking.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.follow.service.FollowServiceImpl;

@RestController
@RequestMapping("/follow")
public class RestFollowController {

    @Autowired
    private FollowServiceImpl followService;

}
