package com.psychopath.dogstalking.mFollow.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.ItemDto;
import com.psychopath.dogstalking.mFollow.dto.AchievementDto;
import com.psychopath.dogstalking.mFollow.dto.AchievementResultDto;
import com.psychopath.dogstalking.mFollow.dto.CoinCategoryDto;
import com.psychopath.dogstalking.mFollow.dto.ItemShopDto;
import com.psychopath.dogstalking.mFollow.dto.OrderCoinDto;
import com.psychopath.dogstalking.mFollow.dto.OrderItemDto;

import java.util.List;

@Mapper
public interface MfollowSqlMapper {

    public List<CoinCategoryDto> selectCoinProductList();

    public List<ItemShopDto> selectShopItemList();

    public ItemShopDto selectItemInfo(int item_pk);

    public CoinCategoryDto selectCoinInfo(int coin_category_pk);

    public UserDto selectUserDtoInfo(int user_pk);

    public void insertOrderCoinDto(OrderCoinDto orderCoinDto);

    public int selectMyCoinCount(int user_pk);

    public int selectMyItemCount(@RequestParam("user_pk") int user_pk,
                                @RequestParam("item_pk") int item_pk);

    public void insertOrderItemDto(OrderItemDto orderItemDto);

    public List<AchievementDto> selectAchievementList();
    public String selectAchievementCategoryName(int ach_catogory_pk);
    public int selectBeforeAchCount(@RequestParam("achievement_pk") int achievement_pk,
                                    @RequestParam("ach_level") int ach_level);                                                      

    public AchievementResultDto selectOneAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                      
    public AchievementResultDto selectTwoAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);  
    public AchievementResultDto selectThreeAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                                
    public AchievementResultDto selectFourAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                      
    public AchievementResultDto selectFiveAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);  
    public AchievementResultDto selectSixAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                                
    public AchievementResultDto selectSevenAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                      
    public AchievementResultDto selectEightAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);  
    public AchievementResultDto selectNineAchievementInfo(@RequestParam("user_pk") int user_pk,
                                                      @RequestParam("achievement_pk") int achievement_pk);                                                                
}
