package com.psychopath.dogstalking.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auction/*")
public class AuctionController {


    @RequestMapping("mainPage")
    public String mainPage(){

        return "/auction/mainPage";
    }

    @RequestMapping("registerGoodsArticleCategoryPage")
    public String registerGoodsArticleCategoryPage(){

        return "/auction/registerGoodsArticleCategoryPage";
    }
    

    @RequestMapping("registerGoodsArticleInfoPage")
    public String registerGoodsArticleInfoPage(HttpSession session){
        session.setAttribute("auctionImageDtoList", null);

        return "/auction/registerGoodsArticleInfoPage";
    }

    @RequestMapping("registerGoodsArticleSalesInfoPage")
    public String registerGoodsArticleSalesInfoPage(){


        return "/auction/registerGoodsArticleSalesInfoPage";
    }

    @RequestMapping("goodsListPage")
    public String goodsListPage(){

        return "/auction/goodsListPage";
    }



}
