package com.psychopath.dogstalking.mFollow.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.follow.dto.ItemDto;
import com.psychopath.dogstalking.mFollow.dto.CoinCategoryDto;
import com.psychopath.dogstalking.mFollow.dto.ItemShopDto;
import com.psychopath.dogstalking.mFollow.mapper.MfollowSqlMapper;

@Service
public class MfollowServiceImpl {
    @Autowired
    private MfollowSqlMapper mFollowSqlMapper;

    public List<Map<String,Object>> getCoinProductList(){
        List<Map<String,Object>> list = new ArrayList<>();
        List<CoinCategoryDto> coinList = mFollowSqlMapper.selectCoinProductList();

        for(CoinCategoryDto coinCategoryDto : coinList){
            Map<String, Object> map = new HashMap<>();

            map.put("coinCategoryDto", coinCategoryDto);

            list.add(map);
        }

        return list;
    }
    public List<Map<String,Object>> getShopItemList(){
        List<Map<String,Object>> list = new ArrayList<>();
        List<ItemShopDto> itemList = mFollowSqlMapper.selectShopItemList();

        for(ItemShopDto itemShopDto : itemList){
            Map<String, Object> map = new HashMap<>();

            map.put("itemShopDto", itemShopDto);

            list.add(map);
        }

        return list;
    }

    public ItemShopDto getItemInfo(int item_pk){
        return mFollowSqlMapper.selectItemInfo(item_pk);
    }
    public CoinCategoryDto getCoinInfo(int coin_category_pk){
        return mFollowSqlMapper.selectCoinInfo(coin_category_pk);
    }
}
