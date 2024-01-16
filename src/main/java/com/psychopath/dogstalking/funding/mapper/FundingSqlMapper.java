package com.psychopath.dogstalking.funding.mapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.dto.FundingWishlistDto;

@Mapper
public interface FundingSqlMapper {

    //상품 등록
    public void insertProductInfo(FundingProductDto productDto);
    //상품 리스트 불러오기
    public List<FundingProductDto> fundingListCall();
    //유저 정보 가져오기
    public UserDto selectUserInfo(int user_pk);
    
    //새소식 올리기
    public void insertNews(FundingNewsDto paraFundingNewsDto);
    //새소식 불러오기
    public List<FundingNewsDto> selectNewsById(int product_pk);
    //제품 상세페이지에서 보일 새소식 하나만 뽑아오기
    public FundingNewsDto selectNewsForDetail(int product_pk);
    //결제 총액을 목표액에 대비해 %로 나타내기

    public void insertCheeringText(FundingCheeringDto paraCheeringDto);
    //마감일 계산해서 가져오기
    public int countFinishDay(int product_pk);
    //개별 상품의 총매출 가져오기
    public int totalSales(int product_pk);
    //상품의 매출총액과 목표금액 대비해서 퍼센트로 구하기
    public int countPercentByPk(int pk);
    //상품 찜하기 
    public void insertWish(FundingWishlistDto paraWishDto);
    public void deleteWish(FundingWishlistDto paraWishDto);
    public int selectWistlistByPk(FundingWishlistDto paraWishDto);
    // 개별상품 페이지 위해 상품정보 가져오기 
    public FundingProductDto selectProductInfo(int pk);
    
    

}
