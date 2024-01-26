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
import com.psychopath.dogstalking.trade.dto.WishListDto;
import com.psychopath.dogstalking.trade.mapper.TradeSqlMapper;

@Service
public class TradeServiceImpl {

    @Autowired
    private TradeSqlMapper tradeMapper;

    public List<DogDto> getDogList(int userPk){

        return tradeMapper.getDogListByUser(userPk);
    }

    // 글 등록
    public void registerCareArticle(TradeArticleDto tradeArticleDto, int carePrice, int[] careDogList){
        int articlePk = tradeMapper.createArticlePk();
        System.out.println(articlePk);
        System.out.println("------------");
        tradeArticleDto.setPk(articlePk);
        System.out.println(tradeArticleDto.getContent());
        System.out.println(tradeArticleDto.getLocation());
        System.out.println(tradeArticleDto.getPk());
        System.out.println(tradeArticleDto.getTitle());
        System.out.println(tradeArticleDto.getEnd_care_date());
        System.out.println(tradeArticleDto.getUser_pk());
        tradeMapper.insertTradeArticleDto(tradeArticleDto);

        CarePriceDto carePriceDto = new CarePriceDto();
        carePriceDto.setPrice(carePrice);
        carePriceDto.setArticle_pk(articlePk);
        tradeMapper.insertCarePrice(carePriceDto);
        System.out.println(carePrice);
        System.out.println(articlePk);
        System.out.println("------------");



        for(int dogPk : careDogList){
            
            CareDogDto careDogDto = new CareDogDto();
            careDogDto.setArticle_pk(articlePk);
            careDogDto.setDog_pk(dogPk);
            tradeMapper.insertCareDog(careDogDto);
            System.out.println(dogPk);
            System.out.println(articlePk);
            System.out.println("------------");
        }
    }

    //글 리스트
    public List<Map<String, Object>> getArticleList(int sessionUserPk){

        List<TradeArticleDto> articleList = tradeMapper.getArticleList();
        List<Map<String, Object>> list = new ArrayList<>();

        for(TradeArticleDto tradeArticleDto : articleList){
            if(tradeArticleDto.getUser_pk() == sessionUserPk){
                continue;
            }else{
                Map<String, Object> map = new HashMap<>();

                int userPk = tradeArticleDto.getUser_pk();
                UserDto userDto = tradeMapper.getUserDto(userPk);
    
                int articlePk = tradeArticleDto.getPk();
                int carePrice = tradeMapper.getCarePriceByArticlePk(articlePk);
    
                List<CareDogDto> careDogList = tradeMapper.getCareDogListByArticlePk(articlePk);
                
                WishListDto wishListDto = new WishListDto();
                wishListDto.setUser_pk(sessionUserPk);
                wishListDto.setArticle_pk(tradeArticleDto.getPk());
                int countWishList = tradeMapper.getCountWishListByUserPkAndArticlePk(wishListDto);

                map.put("userDto", userDto);
                map.put("tradeArticleDto", tradeArticleDto);
                map.put("carePrice", carePrice);
                map.put("careDogList", careDogList);
                map.put("countWishList", countWishList);
    
                list.add(map);

            }
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

    public List<ChatMessageDto> getChatMessage(int chatRoomPk, int sessionUserPk){
        List<ChatMessageDto> list = tradeMapper.getChatMessageByChatRoomPk(chatRoomPk);
        
        for(ChatMessageDto chatMessageDto : list){
            chatMessageDto.getIsRead();

            if(chatMessageDto.getIsRead().equals("N") && chatMessageDto.getUser_pk() != sessionUserPk){
                tradeMapper.updateChatMessageIsRead(chatMessageDto.getPk());
            }
        }


        return tradeMapper.getChatMessageByChatRoomPk(chatRoomPk);
    }

    public void toggleWishList(WishListDto wishListDto){
        
        int count = tradeMapper.getCountWishListByUserPkAndArticlePk(wishListDto);

        if(count == 0){
            tradeMapper.insertWishList(wishListDto);
        }else{
            tradeMapper.deleteWishList(wishListDto);
        }

    }


    // public void chatMessageRead(int messagePk){

    //     tradeMapper.updateChatMessageIsRead(messagePk);
    // }


}
