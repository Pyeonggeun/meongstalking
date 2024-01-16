package com.psychopath.dogstalking.funding.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
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



}
