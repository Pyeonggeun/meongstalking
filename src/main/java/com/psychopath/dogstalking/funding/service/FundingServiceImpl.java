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
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
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
        List<Map<String,Object>> fundingList = new ArrayList<>();
        List<FundingProductDto> fundingProductDtoList = fundingSqlMapper.fundingListCall();
        System.out.println(fundingProductDtoList);

        for(FundingProductDto eleproductDto : fundingProductDtoList){
            UserDto userinfo = fundingSqlMapper.selectUserInfo(eleproductDto.getUser_pk()); 

            //잔여일
            int f_day= fundingSqlMapper.countFinishDay(eleproductDto.getProduct_pk());
            //총 매출
            int t_sales = fundingSqlMapper.totalSales(eleproductDto.getProduct_pk());

            
            

            Map<String,Object> productMap = new HashMap<>();                           
            productMap.put("list",eleproductDto);
            productMap.put("user",userinfo);
            productMap.put("f_day",f_day);
            productMap.put("t_sales",t_sales);
            fundingList.add(productMap);                   
        }
        return fundingList;
    }


    // //잔여일 가져오기 
    // public LocalDate countFinishDay(int product_pk){
    //     return fundingSqlMapper.countFinishDay(product_pk);
    // }
    
    // //상품별 총 매출액 가져오기
    // public int totalSales(int product_pk){
    //     return fundingSqlMapper.totalSales(product_pk);
    // }

    //개별상품 정보 가져오기 
    public Map<String,Object> selectProductInfo(int pk){
        //개별상품 정보 가져오기
        FundingProductDto fundingProductDto =fundingSqlMapper.selectProductInfo(pk);
        //상품 상세 페이지에서 보일 새소식 1개만 뽑아보내기
        FundingNewsDto fundingNewsDto = fundingSqlMapper.selectNewsForDetail(pk);
        //상품 총 매출
        int t_sales = fundingSqlMapper.totalSales(pk);
        //목표금액 대비 퍼센트 도출
        int percent = fundingSqlMapper.countPercentByPk(pk);
        Map<String,Object> productInfo = new HashMap<>(); 
        productInfo.put("detail",fundingProductDto);
        productInfo.put("news",fundingNewsDto);
        productInfo.put("count", t_sales);
        productInfo.put("percent",percent);
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
    public void insertCheeringText(FundingCheeringDto paraCheeringDto){
        fundingSqlMapper.insertCheeringText(paraCheeringDto);
    }

    //찜하기
    public void insertWish(FundingWishlistDto paraWishDto){
        fundingSqlMapper.insertWish(paraWishDto);
    }
    public void deleteWish(FundingWishlistDto paraWishDto){
        fundingSqlMapper.deleteWish(paraWishDto);
    }
    public int selectWistlistByPk(FundingWishlistDto paraWishDto){
        fundingSqlMapper.selectWistlistByPk(paraWishDto);
        return fundingSqlMapper.selectWistlistByPk(paraWishDto);
    }
    



}
