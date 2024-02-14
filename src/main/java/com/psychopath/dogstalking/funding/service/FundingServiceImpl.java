package com.psychopath.dogstalking.funding.service;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingOrderDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.dto.FundingReviewDto;
import com.psychopath.dogstalking.funding.dto.FundingWishlistDto;
import com.psychopath.dogstalking.funding.mapper.FundingSqlMapper;



@Service
public class FundingServiceImpl {
    @Autowired
    private FundingSqlMapper fundingSqlMapper;

    //펀딩 상품 등록
    public void insertProductInfo(FundingProductDto productDto){
        fundingSqlMapper.insertProductInfo(productDto);

    }

    //메인 페이지 호출되는 펀딩 상품리스트 불러오기
    public List<Map<String,Object>> fundingListCall(){
        System.out.println("서비스 fundingListCall 시작");
        List<Map<String,Object>> fundingList = new ArrayList<>();
        List<FundingProductDto> fundingProductDtoList = fundingSqlMapper.fundingListCall();

        for(FundingProductDto eleproductDto : fundingProductDtoList){
            UserDto userinfo = fundingSqlMapper.selectUserInfo(eleproductDto.getUser_pk()); 
            //잔여일
            int f_day= fundingSqlMapper.countFinishDay(eleproductDto.getProduct_pk());
            //목표대비 매출액 퍼센트
            int percent = fundingSqlMapper.countPercentByPk(eleproductDto.getProduct_pk());
            //총매출
            int t_sales = fundingSqlMapper.totalSales(eleproductDto.getProduct_pk());
            //좋아요를 눌렀는지 안 눌렀는지 
            // int wishOrNot = fundingSqlMapper.countMyWish(eleproductDto.getProduct_pk());

            Map<String,Object> productMap = new HashMap<>();                           
            productMap.put("list",eleproductDto);
            productMap.put("user",userinfo);
            productMap.put("f_day",f_day);
            productMap.put("percent",percent);
            productMap.put("t_sales",t_sales);
    
            fundingList.add(productMap);                   
        }
        System.out.println("서비스 fundingListCall 마무리 리턴직전");
        return fundingList;
    }

    //판매회원마이페이지에 들어갈 상품 리스트
    public List<FundingProductDto> fundingListCallForPk(int user_pk){
        return fundingSqlMapper.fundingListCallForPk(user_pk);
    }

    //회원마이페이지에 들어갈 정보 구매 상품과 엮은 상품디티오
    public List<Map<String,Object>> selectOrderListByPk(int user_pk){
        List<Map<String,Object>> orderList = new ArrayList<>();
        List<FundingOrderDto> orderDto = fundingSqlMapper.selectOrderList(user_pk);
        for(FundingOrderDto eleOrder : orderDto){
            FundingProductDto productInfo= fundingSqlMapper.selectProductInfo(eleOrder.getProduct_pk());
        
            Map<String,Object> map = new HashMap<>();
            map.put("order", eleOrder);
            map.put("product", productInfo);
            orderList.add(map);
        }
        return orderList;
    }


    //회원마이페이지에 들어갈 정보 찜한 상품과 엮은 상품디티오
    public List<Map<String,Object>> selectWishlistByPk(int user_pk){
        List<Map<String,Object>> WishList = new ArrayList<>();
        List<FundingWishlistDto> wishDto = fundingSqlMapper.selectWishList(user_pk);
        for(FundingWishlistDto eleWish : wishDto){
            FundingProductDto productInfo= fundingSqlMapper.selectProductInfo(eleWish.getProduct_pk());

            Map<String,Object> map = new HashMap<>();
            map.put("wish", eleWish);
            map.put("product", productInfo);
            WishList.add(map);
        }
        return WishList;
    }

    //회원마이페이지에 집어 넣을 잡다한 map
    public Map<String,Object> selectForMyPage(int user_pk){

        Map<String,Object> countMap = new HashMap<>();
        countMap.put("countOrder", fundingSqlMapper.countOrder(user_pk));
        countMap.put("countWish", fundingSqlMapper.countWish(user_pk));

        return countMap;
    }

    //개별상품 정보 가져오기 
    public Map<String,Object> selectProductInfo(int pk){
        //개별상품 정보 가져오기
        FundingProductDto fundingProductDto =fundingSqlMapper.selectProductInfo(pk);
        //상품 상세 페이지에서 보일 새소식 1개만 뽑아보내기
        String fundingNewsDto = fundingSqlMapper.selectNewsForDetail(pk);
        //상품 상세 페이지에서 보일 새소식 리스트 뽑아보내기
        List<FundingNewsDto> newsList = fundingSqlMapper.selectNewsById(pk);
        //상품 상세 페이지에서 보일 새소식 리스트를 카운트 하기 
        int c_news = fundingSqlMapper.countNewsByPk(pk);
        //상품 총 매출
        int t_sales = fundingSqlMapper.totalSales(pk);
        //목표금액 대비 퍼센트 도출
        int percent = fundingSqlMapper.countPercentByPk(pk);
        //잔여일
        int f_day= fundingSqlMapper.countFinishDay(pk);
        //상품 구매 총 횟수
        int c_purchase = fundingSqlMapper.countPurchase(pk);

        Map<String,Object> productInfo = new HashMap<>(); 
        productInfo.put("detail",fundingProductDto);
        productInfo.put("news",fundingNewsDto);
        productInfo.put("newsList",newsList);
        productInfo.put("c_news",c_news);
        productInfo.put("count", t_sales);
        productInfo.put("percent",percent);
        productInfo.put("f_day",f_day);
        productInfo.put("t_sales",t_sales);
        productInfo.put("c_purchase",c_purchase);
        return productInfo;
    }

    //펀딩 상품 별 새소식 등록하기
    public void insertNews(FundingNewsDto paraFundingNewsDto){
        fundingSqlMapper.insertNews(paraFundingNewsDto);
    }

    //펀딩 상품 별 새소식 가져오기
    public List<FundingNewsDto> selectNewsById(int product_pk){
        return fundingSqlMapper.selectNewsById(product_pk);
    }

    //응원글 집어넣기
    public void insertCheering(FundingCheeringDto paraCheeringDto){
        fundingSqlMapper.insertCheering(paraCheeringDto);
    }
    //응원글 뽑아오기
    public List<Map<String,Object>> selectCheering(int product_pk){

        List<Map<String,Object>> cheeringList = new ArrayList<>();
        List<FundingCheeringDto> cheeringDtoList = fundingSqlMapper.selectCheering(product_pk);
        for(FundingCheeringDto eleCheeringDto : cheeringDtoList){

            UserDto userinfo = fundingSqlMapper.selectUserInfo(eleCheeringDto.getUser_pk());

            Map<String,Object> map = new HashMap<>();
            map.put("cheering",eleCheeringDto);
            map.put("user", userinfo);
            cheeringList.add(map);
        }
        return cheeringList;
    }

    //찜하기
    public void toggleWishProduct(FundingWishlistDto paraWishDto){
        if(fundingSqlMapper.countMyWish(paraWishDto) > 0){
            fundingSqlMapper.deleteWish(paraWishDto);
        }else{
            fundingSqlMapper.insertWish(paraWishDto);
        }   
    }

    public boolean reloadWish(FundingWishlistDto paraWishDto){
        System.out.println("rest reloadWish 실행");
        return fundingSqlMapper.countMyWish(paraWishDto) > 0 ? true : false;
    }


    // public void insertWish(FundingWishlistDto paraWishDto){
    //     fundingSqlMapper.insertWish(paraWishDto);
    // }
    // public void deleteWish(FundingWishlistDto paraWishDto){
    //     fundingSqlMapper.deleteWish(paraWishDto);
    // }

    //결제정보 집어넣기
    public void insertOrder(FundingOrderDto fundingOrderDto){
        fundingSqlMapper.insertOrder(fundingOrderDto);
    }
    
    //회원이 구매한 상품의 상세 활동 페이지에서 보일 정보
    public Map<String,Object> selectOrderInfo(int order_pk){
        FundingOrderDto orderDto = fundingSqlMapper.selectOrderByOrderPk(order_pk);
        FundingProductDto productDto = fundingSqlMapper.selectProductInfo(orderDto.getProduct_pk());
        UserDto userDto = fundingSqlMapper.selectUserInfo(orderDto.getUser_pk());

        Map<String,Object> OrderMap = new HashMap<>();
        OrderMap.put("order",orderDto);
        OrderMap.put("product",productDto);
        OrderMap.put("user",userDto);
        
        return OrderMap;
    }

    //리뷰넣기
    public void insertReview(FundingReviewDto paraReviewDto){
        fundingSqlMapper.insertReview(paraReviewDto);
    }

    //리뷰 엮어서 가져오기
    public List<Map<String,Object>> selectReview(int product_pk){
        List<Map<String,Object>> reviewList = new ArrayList<>();
        List<FundingReviewDto> reviewDtoList = fundingSqlMapper.selectReview(product_pk);
        for(FundingReviewDto eleFundingReview :reviewDtoList){
            UserDto userinfo = fundingSqlMapper.selectUserInfo(eleFundingReview.getUser_pk());

            Map<String,Object> map = new HashMap<>();
            map.put("review", eleFundingReview);
            map.put("user", userinfo);
            reviewList.add(map);
        }
        
        return reviewList;
    }

    //리뷰 체크 위해 카운트 해오기
    public int selectOrderCount(int order_pk){
        return fundingSqlMapper.selectOrderCount(order_pk);
    }

    //판매회원 위한 간략한 통계구하기
    public Map<String,Object> selectStatistics(int user_pk){
        
        Map<String,Object> statsMap = new HashMap<>();
        // 주문 찜 응원 리뷰 하루단위 
        statsMap.put("order", fundingSqlMapper.selectOrderStatsDay(user_pk));
        statsMap.put("review", fundingSqlMapper.selectReviewStatsDay(user_pk));
        statsMap.put("cheering", fundingSqlMapper.selectCheeringStatsDay(user_pk));
        statsMap.put("wishlist", fundingSqlMapper.selectWishlistStatsDay(user_pk));
        statsMap.put("success",fundingSqlMapper.selectCountSuccessGoal(user_pk));
        statsMap.put("fail",fundingSqlMapper.selectCountFailGoal(user_pk));
        return statsMap;

    }

    //목표율 달성한 
    public int selectCountSuccessGoal(int user_pk){
        return fundingSqlMapper.selectCountSuccessGoal(user_pk);
    }

    //목표율 미달성한
    public int selectCountFailGoal(int user_pk){
        return fundingSqlMapper.selectCountFailGoal(user_pk);

    }

    //응원 페이지에서 보일 물건별 응원수 카운트
    public int countCheeringByPk(int product_pk){
        return fundingSqlMapper.countCheeringByPk(product_pk);
    }
    //물건별 새소식 페이지에서 보일 새소식 카운트
    public int countNewsByPk(int product_pk){
        return fundingSqlMapper.countNewsByPk(product_pk);
    }

    public List<Map<String, Object>> selectFundingMainList(){
        return fundingSqlMapper.selectFundingMainList();
    }

}
