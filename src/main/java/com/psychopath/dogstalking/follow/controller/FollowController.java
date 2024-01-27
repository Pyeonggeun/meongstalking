package com.psychopath.dogstalking.follow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @RequestMapping("mainPage")
    public String mainPage() {

        return "follow/mainPage";
    }
    
    @RequestMapping("testPage")
    public String testPage() {


        return "follow/testPage";
    }

}
