package com.psychopath.dogstalking.mFollow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mFollow/*")
public class MfollowController {

    @RequestMapping("testModal")
    public String testModal(){
        return "/mFollow/testModal";
    }
    @RequestMapping("testAchievement")
    public String testAchievement(){
        return "/mFollow/testAchievement";
    }
    @RequestMapping("payRequestPage")
    public String payRequestPage(){
        return "/mFollow/payRequestPage";
    }

    @RequestMapping("requestResultPage")
    public String requestResultPage(){
        return "/mFollow/requestResultPage";
    }
    
}
