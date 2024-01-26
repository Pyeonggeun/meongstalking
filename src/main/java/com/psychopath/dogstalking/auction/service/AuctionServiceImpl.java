package com.psychopath.dogstalking.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.psychopath.dogstalking.auction.dto.AuctionCategoryDto;
import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.auction.dto.BidDto;
import com.psychopath.dogstalking.auction.dto.ChatDto;
import com.psychopath.dogstalking.auction.mapper.AuctionSqlMapper;
import com.psychopath.dogstalking.dto.UserDto;

@Service
public class AuctionServiceImpl {

    @Autowired
    private AuctionSqlMapper auctionMapper;


    public List<AuctionCategoryDto> getCategory(){

        return auctionMapper.getCategory();
    }

    public void registerGoods(AuctionGoodsDto auctionGoodsDto, String[] images){

		if(images.length == 1){
			auctionGoodsDto.setImage_link(images[0]);
		}else{
			for(int x = 0 ; x < images.length-1 ; x++){
				if(x == 0){
					auctionGoodsDto.setImage_link(images[x]);
				}else{
                    AuctionImageDto auctionImageDto = new AuctionImageDto();
                    auctionImageDto.setGoods_pk(auctionMapper.createGoodsPk());
                    auctionImageDto.setImage_link(images[x]);
                    auctionMapper.insertImageDto(auctionImageDto);
				}
			}
		}


        auctionMapper.insertGoodsDto(auctionGoodsDto);

    }

    public List<Map<String, Object>> getGoodsListByCategoryPk(int category_pk){

        List<AuctionGoodsDto> goodsList = auctionMapper.getGoodsListByCategoryPk(category_pk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(AuctionGoodsDto auctionGoodsDto : goodsList){
            Map<String, Object> map = new HashMap<>();
            
            int userPk = auctionGoodsDto.getUser_pk();
            UserDto userDto = auctionMapper.getUserInfoByPk(userPk);

            map.put("auctionGoodsDto", auctionGoodsDto);
            map.put("userDto", userDto);

            list.add(map);
        }


        return list;
    }



    public Map<String, Object> getGoodsDetailInfo(int goodsPk){

        Map<String, Object> map = new HashMap<>();

        AuctionGoodsDto auctionGoodsDto = auctionMapper.getGoodsDto(goodsPk);

        UserDto userDto = auctionMapper.getUserInfoByPk(auctionGoodsDto.getUser_pk());

        AuctionCategoryDto auctionCategoryDto = auctionMapper.getCategoryDto(auctionGoodsDto.getCategory_pk());

        map.put("auctionGoodsDto", auctionGoodsDto);
        map.put("userDto", userDto);
        map.put("auctionCategoryDto", auctionCategoryDto);

        return map;
    }

    public void registerChatMessage(ChatDto chatDto){

        auctionMapper.insertChatDto(chatDto);
    }


    public List<Map<String, Object>> getChatList(int goodsPk){
        List<ChatDto> chatList = auctionMapper.getChatList(goodsPk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(ChatDto chatDto : chatList){
            Map<String, Object> map = new HashMap<>();
            
            UserDto userDto = auctionMapper.getUserInfoByPk(chatDto.getUser_pk());
        
            map.put("chatDto", chatDto);
            map.put("userDto", userDto);
        
            list.add(map);
        }

        return list;
    }

    public Map<String, Object> getBidList(int goodsPk){
        Map<String, Object> map = new HashMap<>();

        List<BidDto> bidList = auctionMapper.getBidListByGoodsPk(goodsPk);
        List<Map<String, Object>> list = new ArrayList<>();

        int highestBidPrice = auctionMapper.getHighestBidPrice(goodsPk);

        for(BidDto bidDto : bidList){
            Map<String, Object> bidMap = new HashMap<>();

            int userPk = bidDto.getUser_pk();
            UserDto userDto = auctionMapper.getUserInfoByPk(userPk);

            bidMap.put("userDto", userDto);
            bidMap.put("bidDto", bidDto);

            list.add(bidMap);
        }

        map.put("bidList", list);
        map.put("highestBidPrice", highestBidPrice);

        return map;
    }

    public boolean registerBid(BidDto bidDto){
        int highestBidPrice = auctionMapper.getHighestBidPrice(bidDto.getGoods_pk());
        int bidPrice = bidDto.getBid_price();
        
        boolean isBid = false;

        if(bidPrice <= highestBidPrice){
            return isBid;
        }else{
            auctionMapper.insertBidDto(bidDto);
            isBid = true;
            return isBid;
        }
    }


}
