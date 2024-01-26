package com.psychopath.dogstalking.auction.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

import com.psychopath.dogstalking.auction.dto.AuctionCategoryDto;
import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;

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
}
