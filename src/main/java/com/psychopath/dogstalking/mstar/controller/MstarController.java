package com.psychopath.dogstalking.mstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mstar/*")
public class MstarController {

    @RequestMapping("profilePage")
    public String profilePage(){

        return "/mstar/profilePage";
    }
    @RequestMapping("mainListPage")
    public String mainListPage(){

        return "/mstar/mainListPage";
    }
    @RequestMapping("updateProfilePage")
    public String updateProfilePage(){

        return "/mstar/updateProfilePage";
    }
}
