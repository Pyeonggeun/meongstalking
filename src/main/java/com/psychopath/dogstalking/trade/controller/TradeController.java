package com.psychopath.dogstalking.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.service.TradeServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/trade/*")
public class TradeController {

    @Autowired
    private TradeServiceImpl tradeService;

    @RequestMapping("mainPage")
    public String mainPage(){

        return "/trade/mainPage";
    }

    @RequestMapping("registerArticlePage")
    public String registerArticlePage(){


        return "/trade/registerArticlePage";
    }

    @RequestMapping("registerArticlePageSecond")
    public String registerArticlePageSecond(int articlePk){
        
        return "/trade/registerArticlePageSecond";
    }


}
