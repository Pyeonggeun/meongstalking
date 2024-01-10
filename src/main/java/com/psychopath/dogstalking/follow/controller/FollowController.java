package com.psychopath.dogstalking.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psychopath.dogstalking.follow.service.FollowServiceImpl;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowServiceImpl followService;

}
