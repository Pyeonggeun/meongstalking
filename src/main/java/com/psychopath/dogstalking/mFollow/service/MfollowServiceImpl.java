package com.psychopath.dogstalking.mFollow.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.ItemDto;
import com.psychopath.dogstalking.mFollow.dto.AchievementDto;
import com.psychopath.dogstalking.mFollow.dto.AchievementResultDto;
import com.psychopath.dogstalking.mFollow.dto.CoinCategoryDto;
import com.psychopath.dogstalking.mFollow.dto.ItemShopDto;
import com.psychopath.dogstalking.mFollow.dto.OrderCoinDto;
import com.psychopath.dogstalking.mFollow.dto.OrderItemDto;
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
    public List<Map<String,Object>> getShopItemList(int user_pk){
        List<Map<String,Object>> list = new ArrayList<>();
        List<ItemShopDto> itemList = mFollowSqlMapper.selectShopItemList();

        for(ItemShopDto itemShopDto : itemList){
            Map<String, Object> map = new HashMap<>();
            int item_pk = itemShopDto.getItem_pk();
            int itemCount =  mFollowSqlMapper.selectMyItemCount(user_pk, item_pk);
            map.put("itemCount", itemCount);
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
    public UserDto getUserInfo(int user_pk){
        return mFollowSqlMapper.selectUserDtoInfo(user_pk);
    }
    public void insertOrderCoin(OrderCoinDto orderCoinDto){
        mFollowSqlMapper.insertOrderCoinDto(orderCoinDto);
    }

    public int getMyCoinCount(int user_pk){
        return mFollowSqlMapper.selectMyCoinCount(user_pk);
    }

    public void inserItemInfo(OrderItemDto orderItemDto){
        mFollowSqlMapper.insertOrderItemDto(orderItemDto);
    }

    public List<Map<String,Object>> getAchievementList(int user_pk){
        List<Map<String,Object>> list = new ArrayList<>();
        List<AchievementDto> achievementList = mFollowSqlMapper.selectAchievementList();

        for(AchievementDto achievementDto : achievementList){
            Map<String, Object> map = new HashMap<>();
            int achievement_pk =  achievementDto.getAchievement_pk();
            AchievementResultDto achievementResultDto = new AchievementResultDto();
            switch (achievement_pk) {
                case 1:
                    achievementResultDto = mFollowSqlMapper.selectOneAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 2:
                    achievementResultDto = mFollowSqlMapper.selectTwoAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 3:
                    achievementResultDto = mFollowSqlMapper.selectThreeAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;               
                case 4:
                    achievementResultDto = mFollowSqlMapper.selectFourAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 5:
                    achievementResultDto = mFollowSqlMapper.selectFiveAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 6:
                    achievementResultDto = mFollowSqlMapper.selectSixAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 7:
                    achievementResultDto = mFollowSqlMapper.selectSevenAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;               
                case 8:
                    achievementResultDto = mFollowSqlMapper.selectEightAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
                case 9:
                    achievementResultDto = mFollowSqlMapper.selectNineAchievementInfo(user_pk, achievement_pk);
                    map.put("achievementResultDto", achievementResultDto);
                    break;
            }
            int ach_catogory_pk = achievementDto.getAch_catogory_pk();
            String categoryName = mFollowSqlMapper.selectAchievementCategoryName(ach_catogory_pk);
            int ach_level = achievementResultDto.getAch_level();
            int beforeCount = 0;
            if(ach_level != 1){
                beforeCount = mFollowSqlMapper.selectBeforeAchCount(achievement_pk, ach_level);
            }
            

            map.put("beforeCount", beforeCount);
            map.put("categoryName", categoryName);
            map.put("achievementDto", achievementDto);

            list.add(map);
        }

        return list;
    }
}
