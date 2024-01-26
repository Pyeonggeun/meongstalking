package com.psychopath.dogstalking.funding.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingOrderDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.dto.FundingReviewDto;
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
    public String selectNewsForDetail(int product_pk);
    //제품 상세페이지에서 보일 새소식 갯수 뽑아가기
    public int countNewsByPk(int product_pk);
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
    public int countForWish(FundingWishlistDto paraWishDtos);


    // 개별상품 페이지 위해 상품정보 가져오기 
    public FundingProductDto selectProductInfo(int pk);

    //일반회원 마이페이지에서 보일 회원이 주문한 상품 리스트
    public List<FundingOrderDto> selectOrderList(int user_pk);
    //일반회원 마이페이지에서 보일 회원이 찜한 상품 리스트
    public List<FundingWishlistDto> selectWishList(int user_pk);

    ////일반회원 마이페이지에서 보일 카운드들
    public int countOrder(int user_pk);
    public int countWish(int user_pk);

    //판매 회원 마이페이지에서 보일 회원이 업로드 한 상품 리스트 
    public List<FundingProductDto> fundingListCallForPk(int user_pk); 

    //결제 집어넣기
    public void insertOrder(FundingOrderDto fundingOrderDto);

    //결제창에서 회원이 상품에 대해 결제해야 하는 결제 총액 가져오기
    public int totalPayment();

    //결제 마지막에 날짜 추가되는 메소드
    public void updateOrderDate(FundingOrderDto fundingOrderDto);
    
    //응원글 집어넣기
    public void insertCheering(FundingCheeringDto fundingCheeringDto);
    //응원글 뽑아오기 
    public List<FundingCheeringDto> selectCheering(int product_pk);
    
    //일반회원 페이지에서 구매한 제품의 상세 활동을 위한 오더 정보 
    public FundingOrderDto selectOrderByOrderPk(int order_pk);

    //리뷰 넣기
    public void insertReview(FundingReviewDto paraReviewDto);

    //리뷰 가져오기
    public List<FundingReviewDto> selectReview(int product_pk);
    //리뷰 썼는지 확인하기 위함
    public int selectOrderCount(int order_pk);

    //7일동안 내가 올린 상품들의 주문량
    public int selectOrderStatsDay(int user_pk);
    //7일동안 내가 올린 상품들에대한 리뷰수
    public int selectReviewStatsDay(int user_pk);
    //7일동안 내가 올린 상품들에 대한 응원수
    public int selectCheeringStatsDay(int user_pk);
    //7일동안 내가 올린 상품들에 대한 찜의 갯수
    public int selectWishlistStatsDay(int user_pk);

    //목표 달성유무 판단해서 숫자로 돌려주기 
    public int selectCountSuccessGoal(int user_pk);
    public int selectCountFailGoal(int user_pk);

}
