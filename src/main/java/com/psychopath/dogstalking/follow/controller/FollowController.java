package com.psychopath.dogstalking.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psychopath.dogstalking.follow.service.FollowSimulator;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowSimulator followSimulator;

    @RequestMapping("mainPage")
    public String mainPage() {

        return "follow/mainPage";
    }

    @RequestMapping("functionPage")
    public String functionPage() {

        return "follow/functionPage";
    }
    
    @RequestMapping("testPage")
    public String testPage() {
        // followSimulator.register();
        // followSimulator.marking();

        return "follow/testPage";
    }

    @RequestMapping("examplePage")
    public String examplePage() {

        return "follow/examplePage";
    }

    @RequestMapping("loginPage")
    public String loginPage() {

        return "follow/loginPage";
    }

}
