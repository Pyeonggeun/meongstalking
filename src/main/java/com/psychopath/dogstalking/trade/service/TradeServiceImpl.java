package com.psychopath.dogstalking.trade.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.CarePriceDto;
import com.psychopath.dogstalking.trade.dto.ChatMessageDto;
import com.psychopath.dogstalking.trade.dto.ChatRoomDto;
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

    public void registerArticleSecond(TradeArticleDto tradeArticleDto){
        tradeMapper.updateTradeArticle(tradeArticleDto);
    }

    public void registerCarePrice(CarePriceDto carePriceDto){
        tradeMapper.insertCarePrice(carePriceDto);
    }

    //글 리스트
    public List<Map<String, Object>> getArticleList(){

        List<TradeArticleDto> articleList = tradeMapper.getArticleList();
        List<Map<String, Object>> list = new ArrayList<>();

        for(TradeArticleDto tradeArticleDto : articleList){

            Map<String, Object> map = new HashMap<>();

            int userPk = tradeArticleDto.getUser_pk();
            UserDto userDto = tradeMapper.getUserDto(userPk);

            int articlePk = tradeArticleDto.getPk();
            int carePrice = tradeMapper.getCarePriceByArticlePk(articlePk);

            List<CareDogDto> careDogList = tradeMapper.getCareDogListByArticlePk(articlePk);

            map.put("userDto", userDto);
            map.put("tradeArticleDto", tradeArticleDto);
            map.put("carePrice", carePrice);
            map.put("careDogList", careDogList);

            list.add(map);
        }
        return list;

    }


    public Map<String, Object> getArticleDetail(int articlePk){

        Map<String, Object> map = new HashMap<>();

        TradeArticleDto tradeArticleDto = tradeMapper.getArticleDto(articlePk);

        int userPk = tradeArticleDto.getUser_pk();
        UserDto userDto = tradeMapper.getUserDto(userPk);

        int carePrice = tradeMapper.getCarePriceByArticlePk(articlePk);

        List<CareDogDto> careDogList = tradeMapper.getCareDogListByArticlePk(articlePk);

        map.put("userDto", userDto);
        map.put("tradeArticleDto", tradeArticleDto);
        map.put("carePrice", carePrice);
        map.put("careDogList", careDogList);

        return map;
    }

    public int createChatRoom(int articlePk, int senderPk){
        
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        
        TradeArticleDto tradeArticleDto = tradeMapper.getArticleDto(articlePk);
        int recieverPk = tradeArticleDto.getUser_pk();

        chatRoomDto.setArticle_pk(articlePk);
        chatRoomDto.setSender_pk(senderPk);
        chatRoomDto.setReciever_pk(recieverPk);

        tradeMapper.insertChatRoomDto(chatRoomDto);

        return tradeMapper.getRecentChatRoomPk();
    }


    public void registerChatMessage(ChatMessageDto chatMessageDto){

        tradeMapper.insertChatMessage(chatMessageDto);
    }

    public List<Map<String, Object>> getChatRoomList(int userPk){

        List<ChatRoomDto> chatRoomList = tradeMapper.getChatRoomList(userPk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(ChatRoomDto chatRoomDto : chatRoomList){
            Map<String, Object> map = new HashMap<>();

            int senderPk = chatRoomDto.getSender_pk();
            int recieverPk = chatRoomDto.getReciever_pk();

            if(userPk != senderPk){
                UserDto userDto = tradeMapper.getUserDto(senderPk);
                map.put("theOtherUserDto", userDto);                
            }else{
                UserDto userDto = tradeMapper.getUserDto(recieverPk);
                map.put("theOtherUserDto", userDto);
            }
            
            map.put("chatRoomDto", chatRoomDto);

            int chatRoomPK = chatRoomDto.getPk();

            map.put("chatMessageList", tradeMapper.getChatMessageByChatRoomPk(chatRoomPK));
            map.put("lastChatMessage", tradeMapper.getlastChatMessage(chatRoomPK));


            list.add(map);
        }

        return list;
    }

    public List<ChatMessageDto> getChatMessage(int chatRoomPk){
        
        
        return tradeMapper.getChatMessageByChatRoomPk(chatRoomPk);
    }


    public void chatMessageRead(int messagePk){

        tradeMapper.updateChatMessageIsRead(messagePk);
    }


}
