package com.psychopath.dogstalking.trade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.CarePriceDto;
import com.psychopath.dogstalking.trade.dto.ChatMessageDto;
import com.psychopath.dogstalking.trade.dto.ChatRoomDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;

@Mapper
public interface TradeSqlMapper {

    public List<DogDto> getDogListByUser(int userPk);

    public void insertTradeArticleDto(TradeArticleDto tradeArticleDto);

    public int getRecentTradeArticlePk();

    public void insertCareDog(CareDogDto careDogDto);

    public void updateTradeArticle(TradeArticleDto tradeArticleDto);

    public void insertCarePrice(CarePriceDto carePriceDto);

    //돌봄 글 리스트
    public List<TradeArticleDto> getArticleList();

    // 유저 정보
    public UserDto getUserDto(int userPk);

    // 돌봄가격
    public int getCarePriceByArticlePk(int articlePk);

    // 돌봄 강아지
    public List<CareDogDto> getCareDogListByArticlePk(int articlePk);

    // 돌봄 글
    public TradeArticleDto getArticleDto(int articlePk);

    // 채팅방 생성
    public void insertChatRoomDto(ChatRoomDto chatRoomDto);

    // 생성된 채팅방 pk 가져오기
    public int getRecentChatRoomPk();

    // 메시지 등록
    public void insertChatMessage(ChatMessageDto chatMessageDto);

    // 채팅방 목록 가져오기
    public List<ChatRoomDto> getChatRoomList(int userPk);

    // 채팅 메시지 리스트 가져오기
    public List<ChatMessageDto> getChatMessageByChatRoomPk(int chatRoomPk);

    // 채팅 메시지 읽음으로 업데이트
    public void updateChatMessageIsRead(int messagePk);

    // 마지막 채팅 메시지 출력
    public ChatMessageDto getlastChatMessage(int chatRoomPk);

}
