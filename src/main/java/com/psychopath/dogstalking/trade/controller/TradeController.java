package com.psychopath.dogstalking.trade.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String registerArticlePage(int userPk, Model model){
        model.addAttribute("userPk", userPk);

        return "/trade/registerArticlePage";
    }

    @RequestMapping("registerArticlePageSecond")
    public String registerArticlePageSecond(HttpSession session){

        session.setAttribute("careImageDtoList", null);
        
        return "/trade/registerArticlePageSecond";
    }

    @RequestMapping("articleDetailPage")
    public String articleDetailPage(int articlePk, Model model){
        model.addAttribute("articlePk", articlePk);

        return "/trade/articleDetailPage";
    }

    @RequestMapping("chatRoomPage")
    public String chatRoomPage(int chatRoomPk, Model model){
        model.addAttribute("chatRoomPk", chatRoomPk);

        return "/trade/chatRoomPage";
    }

    @RequestMapping("chatRoomListPage")
    public String chatRoomListPage(int userPk, Model model){
        model.addAttribute("userPk", userPk);

        return "/trade/chatRoomListPage";
    }


}
