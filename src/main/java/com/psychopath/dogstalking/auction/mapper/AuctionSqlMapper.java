package com.psychopath.dogstalking.auction.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

import com.psychopath.dogstalking.auction.dto.AuctionCategoryDto;
import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.auction.dto.BidDto;
import com.psychopath.dogstalking.auction.dto.ChatDto;
import com.psychopath.dogstalking.auction.dto.DeliveryDto;
import com.psychopath.dogstalking.auction.dto.PaymentDto;
import com.psychopath.dogstalking.auction.dto.WishlistDto;
import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.CollectionDto;

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

    // 경매 시간 추가
    public void updateGoodsExpiryDatePlusMinutes(AuctionGoodsDto auctionGoodsDto);

    // 추가된 채팅 가져오기
    public List<ChatDto> getAppendChatList(ChatDto chatDto);


    // 내가 입찰한 상품 리스트
    public List<AuctionGoodsDto> getMyBidGoodsList(int user_pk);

    // 나의 가장 높은 입찰 내역
    public BidDto getMyHighestBidByGoods(BidDto bidDto);

    // 상품의 가장 높은 입찰내역
    public BidDto getHighestBidByGoods(BidDto bidDto);

    // 나의 입찰 내역
    public List<BidDto> getMyBidList(BidDto bidDto);

    // 낙찰 상품의 결제 수
    public int getCountPayment(int bid_pk);

    // 결제 insert
    public void insertPayment(PaymentDto paymentDto);

    public BidDto getBidDto(int bidPk);

    public PaymentDto getPaymentDto(int bidPk);

    public DeliveryDto getDeliveryDto(int paymentPk);

    public int getCountDeliveryDto(int paymentPk);

    public List<AuctionGoodsDto> getSaleGoodsList(int userPk);

    public void insertDelivery(DeliveryDto deliveryDto);

    public int getCountWishlist(WishlistDto wishlistDto);

    public void insertWishlist(WishlistDto wishlistDto);

    public void deleteWishlist(WishlistDto wishlistDto);

    public int getCountWishlistByGoods(WishlistDto wishlistDto);

    public int getCountBidByGoods(int goodsPk);


    // 메인페이지 개 정보
    public DogDto getDogInfo(int userPk);

    public int getDogCount(int userPk);

    // 메인페이지 리스트
    public List<Map<String, Object>> mainPageGoodsList();
}
