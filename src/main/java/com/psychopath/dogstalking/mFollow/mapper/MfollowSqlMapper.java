package com.psychopath.dogstalking.mFollow.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.ItemDto;
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
}
