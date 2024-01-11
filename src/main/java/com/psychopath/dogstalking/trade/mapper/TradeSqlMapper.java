package com.psychopath.dogstalking.trade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;

@Mapper
public interface TradeSqlMapper {

    public List<DogDto> getDogListByUser(int userPk);

    public void insertTradeArticleDto(TradeArticleDto tradeArticleDto);

    public int getRecentTradeArticlePk();

    public void insertCareDog(CareDogDto careDogDto);
}
