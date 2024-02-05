package com.psychopath.dogstalking.mFollow.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.follow.dto.ItemDto;
import com.psychopath.dogstalking.mFollow.dto.CoinCategoryDto;
import com.psychopath.dogstalking.mFollow.dto.ItemShopDto;

import java.util.List;

@Mapper
public interface MfollowSqlMapper {

    public List<CoinCategoryDto> selectCoinProductList();

    public List<ItemShopDto> selectShopItemList();

    public ItemShopDto selectItemInfo(int item_pk);

    public CoinCategoryDto selectCoinInfo(int coin_category_pk);
}
