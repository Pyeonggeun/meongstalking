package com.psychopath.dogstalking.funding.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingCheeringDto;
import com.psychopath.dogstalking.funding.dto.FundingNewsDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;

@Mapper
public interface FundingSqlMapper {

    public void insertProductInfo(FundingProductDto productDto);

    public List<FundingProductDto> fundingListCall();

    public UserDto selectUserInfo(int user_pk);
    

    public void insertNews(FundingNewsDto paraFundingNewsDto);
    public List<FundingNewsDto> selectNewsById(int product_pk);

    public void insertCheeringText(FundingCheeringDto paraCheeringDto);
}
