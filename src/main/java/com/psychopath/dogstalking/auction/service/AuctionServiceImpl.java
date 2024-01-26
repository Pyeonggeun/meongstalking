package com.psychopath.dogstalking.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.psychopath.dogstalking.auction.dto.AuctionCategoryDto;
import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.auction.mapper.AuctionSqlMapper;

@Service
public class AuctionServiceImpl {

    @Autowired
    private AuctionSqlMapper auctionMapper;


    public List<AuctionCategoryDto> getCategory(){

        return auctionMapper.getCategory();
    }

    public void registerGoods(AuctionGoodsDto auctionGoodsDto, String[] images){

		if(images.length == 1){
			auctionGoodsDto.setImage_link(images[0]);
		}else{
			for(int x = 0 ; x < images.length-1 ; x++){
				if(x == 0){
					auctionGoodsDto.setImage_link(images[x]);
				}else{
                    AuctionImageDto auctionImageDto = new AuctionImageDto();
                    auctionImageDto.setGoods_pk(auctionMapper.createGoodsPk());
                    auctionImageDto.setImage_link(images[x]);
                    auctionMapper.insertImageDto(auctionImageDto);
				}
			}
		}


        auctionMapper.insertGoodsDto(auctionGoodsDto);

    }

    public List<AuctionGoodsDto> getGoodsListByCategoryPk(int category_pk){

        return auctionMapper.getGoodsListByCategoryPk(category_pk);
    }



}
