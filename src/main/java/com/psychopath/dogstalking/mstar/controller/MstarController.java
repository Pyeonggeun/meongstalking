package com.psychopath.dogstalking.mstar.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.psychopath.dogstalking.mstar.service.MstarServiceImpl;

@Controller
@RequestMapping("/mstar/*")
public class MstarController {

    @Autowired
    private MstarServiceImpl mstarService;

    @RequestMapping("profilePage")
    public String profilePage(int user_pk){

        return "/mstar/profilePage";
    }
    @RequestMapping("mainListPage")
    public String mainListPage(){

        return "/mstar/mainListPage";
    }
    @RequestMapping("updateProfilePage")
    public String updateProfilePage(int user_pk){

        return "/mstar/updateProfilePage";
    }
   
    @RequestMapping("writeArticlePage")
    public String writeArticlePage(int user_pk){

        return "mstar/writeArticlePage";
    }
    @RequestMapping("addStoryStoragePage")
    public String addStoryStoragePage(int user_pk){

        return "mstar/addStoryStoragePage";
    }
    @RequestMapping("uploadStoryPage")
    public String uploadStoryPage(int user_pk){

        return "mstar/uploadStoryPage";
    }
}
