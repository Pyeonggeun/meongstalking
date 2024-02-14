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
    @RequestMapping("anotherProfilePage")
    public String anotherProfilePage(int profile_info_pk){

        return "mstar/anotherProfilePage";
    }
    @RequestMapping("profileArticleListPage")
    public String profileArticleListPage(int profile_info_pk, int user_pk){

        return "mstar/profileArticleListPage"; 
    }
    @RequestMapping("userScrapArticleListPage")
    public String userScrapArticleListPage(int profile_info_pk, int user_pk){

        return "mstar/userScrapArticleListPage";
    }
    @RequestMapping("directListPage")
    public String directListPage(int user_pk){

        return "mstar/directListPage";
    }
    @RequestMapping("directPage")
    public String directPage(int profile_info_pk, int another_info_pk){

        return "mstar/directPage";
    }


    @RequestMapping("modalTextPage")
    public String modalTextPage(){

        return "mstar/modalTextPage";
    }

    @RequestMapping("testNavi")
    public String testNavi(){
        return "mstar/testNavi";
    }
   
}
