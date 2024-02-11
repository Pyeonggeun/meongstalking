package com.psychopath.dogstalking.mFollow.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mFollow.dto.CoinCategoryDto;
import com.psychopath.dogstalking.mFollow.dto.ItemShopDto;
import com.psychopath.dogstalking.mFollow.dto.OrderCoinDto;
import com.psychopath.dogstalking.mFollow.dto.OrderItemDto;
import com.psychopath.dogstalking.mFollow.service.MfollowServiceImpl;

@RestController
@RequestMapping("/mFollow/*")
public class RestMfollowController {
    @Autowired
    private MfollowServiceImpl mFollowService;

    @RequestMapping("loadCoinProductList")
    public RestResponseDto loadCoinProductList(){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String,Object>> list = mFollowService.getCoinProductList();

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }

    @RequestMapping("loadShopItemList")
    public RestResponseDto loadShopItemList(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String,Object>> list = mFollowService.getShopItemList(user_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }

    @RequestMapping("loadItemInfo")
    public RestResponseDto loadItemInfo(int item_pk){
        RestResponseDto responseDto = new RestResponseDto();

        ItemShopDto itemShopDto = mFollowService.getItemInfo(item_pk);

        responseDto.setResult("success");
        responseDto.setData(itemShopDto);

        return responseDto;
    }
    @RequestMapping("loadCoinInfo")
    public RestResponseDto loadCoinInfo(int coin_category_pk){
        RestResponseDto responseDto = new RestResponseDto();

        CoinCategoryDto coinCategoryDto = mFollowService.getCoinInfo(coin_category_pk);

        responseDto.setResult("success");
        responseDto.setData(coinCategoryDto);

        return responseDto;
    }
    @RequestMapping("getUserDto")
    public RestResponseDto getUserDto(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        UserDto userDto = mFollowService.getUserInfo(user_pk);

        responseDto.setResult("success");
        responseDto.setData(userDto);

        return responseDto;
    }
    @RequestMapping("insertOrderCoin")
    public void insertOrderCoin(OrderCoinDto orderCoinDto){
    
        mFollowService.insertOrderCoin(orderCoinDto);
    }
    @RequestMapping("getMyCoin")
    public RestResponseDto getMyCoin(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        int coinCount = mFollowService.getMyCoinCount(user_pk);

        responseDto.setResult("success");
        responseDto.setData(coinCount);

        return responseDto;
    }
    @RequestMapping("insertOrderItem")
    public void insertOrderItem(OrderItemDto orderItmeDto){
    
        mFollowService.inserItemInfo(orderItmeDto);
    }
    @RequestMapping("loadMyAchievementList")
    public RestResponseDto loadMyAchievementList(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String,Object>> list = mFollowService.getAchievementList(user_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }
}