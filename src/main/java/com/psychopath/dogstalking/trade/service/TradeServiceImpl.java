package com.psychopath.dogstalking.trade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;
import com.psychopath.dogstalking.trade.mapper.TradeSqlMapper;

@Service
public class TradeServiceImpl {

    @Autowired
    private TradeSqlMapper tradeMapper;

    public List<DogDto> getDogList(int userPk){

        return tradeMapper.getDogListByUser(userPk);
    }

    public int registerTradeArticleFirst(TradeArticleDto tradeArticleDto){
        tradeMapper.insertTradeArticleDto(tradeArticleDto);
        return tradeMapper.getRecentTradeArticlePk();
    }

    public void registerCareDog(CareDogDto careDogDto){
        tradeMapper.insertCareDog(careDogDto);
    }


}
