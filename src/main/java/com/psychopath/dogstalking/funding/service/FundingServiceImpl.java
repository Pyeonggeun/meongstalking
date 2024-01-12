package com.psychopath.dogstalking.funding.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

    public void insertProductInfo(FundingProductDto productDto){
        fundingSqlMapper.insertProductInfo(productDto);

    }

    public List<Map<String,Object>> fundingListCall(){
        List<Map<String,Object>> fundingList = new ArrayList<>();
        List<FundingProductDto> fundingProductDtoList = fundingSqlMapper.fundingListCall();
        System.out.println(fundingProductDtoList);

        for(FundingProductDto eleproductDto : fundingProductDtoList){
            UserDto userinfo = fundingSqlMapper.selectUserInfo(eleproductDto.getUser_pk()); 
            System.out.println(userinfo.getUser_pk());

            Map<String,Object> productMap = new HashMap<>();                           
            productMap.put("list",eleproductDto);
            productMap.put("user",userinfo);
            
            fundingList.add(productMap);                   
        }
        return fundingList;
    }

    public void insertNews(FundingNewsDto paraFundingNewsDto){
        fundingSqlMapper.insertNews(paraFundingNewsDto);
    }


    public List<FundingNewsDto> selectNewsById(int product_pk){
        return fundingSqlMapper.selectNewsById(product_pk);
    }

    public void insertCheeringText(FundingCheeringDto paraCheeringDto){
        fundingSqlMapper.insertCheeringText(paraCheeringDto);
    }

}
