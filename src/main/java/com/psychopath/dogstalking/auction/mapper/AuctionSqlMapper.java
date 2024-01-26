package com.psychopath.dogstalking.auction.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

import com.psychopath.dogstalking.auction.dto.AuctionCategoryDto;
import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.auction.dto.BidDto;
import com.psychopath.dogstalking.auction.dto.ChatDto;
import com.psychopath.dogstalking.dto.UserDto;

@Mapper
public interface AuctionSqlMapper {

    // 카테고리
    public List<AuctionCategoryDto> getCategory();

    // 상품 등록
    public void insertGoodsDto(AuctionGoodsDto auctionGoodsDto);

    // 상품 pk 가져오기
    public int createGoodsPk();

    // 이미지 등록
    public void insertImageDto(AuctionImageDto auctionImageDto);

    // 카테고리별 상품 리스트, 남은 경매 시간 적은 순으로
    public List<AuctionGoodsDto> getGoodsListByCategoryPk(int category_pk);

    // 유저 정보
    public UserDto getUserInfoByPk(int userPk);

    // 상품 정보
    public AuctionGoodsDto getGoodsDto(int goodsPk);

    // 상품 카테고리
    public AuctionCategoryDto getCategoryDto(int categoryPk);

    // 채팅 입력
    public void insertChatDto(ChatDto chatDto);

    // 채팅 불러오기
    public List<ChatDto> getChatList(int goodsPk);

    // 입찰 리스트 가져오기
    public List<BidDto> getBidListByGoodsPk(int goodsPk);

    // 최고 입찰가 가져오기
    public int getHighestBidPrice(int goodsPk);

    // 입찰하기
    public void insertBidDto(BidDto bidDto);


}
