package com.psychopath.dogstalking.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.psychopath.dogstalking.auction.mapper.AuctionSqlMapper;
import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.trade.dto.WishListDto;

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

    public List<Map<String, Object>> getGoodsListByCategoryPk(int category_pk, int user_pk){

        List<AuctionGoodsDto> goodsList = auctionMapper.getGoodsListByCategoryPk(category_pk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(AuctionGoodsDto auctionGoodsDto : goodsList){
            Map<String, Object> map = new HashMap<>();
            
            int userPk = auctionGoodsDto.getUser_pk();
            UserDto userDto = auctionMapper.getUserInfoByPk(userPk);

            WishlistDto wishlistDto = new WishlistDto();
            wishlistDto.setUser_pk(user_pk);
            wishlistDto.setGoods_pk(auctionGoodsDto.getPk());

            int wishlistCount = auctionMapper.getCountWishlist(wishlistDto);
            int wishlistAllCount = auctionMapper.getCountWishlistByGoods(wishlistDto);

            int bidCount = auctionMapper.getCountBidByGoods(auctionGoodsDto.getPk());

            map.put("auctionGoodsDto", auctionGoodsDto);
            map.put("userDto", userDto);
            map.put("wishlistCount", wishlistCount);
            map.put("wishlistAllCount", wishlistAllCount);
            map.put("bidCount", bidCount);

            list.add(map);
        }


        return list;
    }



    public Map<String, Object> getGoodsDetailInfo(int goodsPk){

        Map<String, Object> map = new HashMap<>();

        AuctionGoodsDto auctionGoodsDto = auctionMapper.getGoodsDto(goodsPk);

        UserDto userDto = auctionMapper.getUserInfoByPk(auctionGoodsDto.getUser_pk());

        AuctionCategoryDto auctionCategoryDto = auctionMapper.getCategoryDto(auctionGoodsDto.getCategory_pk());

        map.put("auctionGoodsDto", auctionGoodsDto);
        map.put("userDto", userDto);
        map.put("auctionCategoryDto", auctionCategoryDto);

        return map;
    }

    public Map<String, Object> getGoodsInfo(int goodsPk){

        Map<String, Object> map = new HashMap<>();

        boolean isBidEnd = false;

        AuctionGoodsDto auctionGoodsDto = auctionMapper.getGoodsDto(goodsPk);
        LocalDateTime expiryDate = auctionGoodsDto.getExpiry_date();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, expiryDate);

        if(duration.isNegative()){

            isBidEnd = true;

        }else{
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;
            long[] remainingTime = new long[4];
            remainingTime[0] = days;
            remainingTime[1] = hours;
            remainingTime[2] = minutes;
            remainingTime[3] = seconds;

            if (days > 0) {
                map.put("remainingTime", remainingTime);
            } else {
                map.put("remainingTime", remainingTime);
            }
        }

        map.put("isBidEnd", isBidEnd);

        return map;
    }

    public void registerChatMessage(ChatDto chatDto){

        auctionMapper.insertChatDto(chatDto);
    }


    public List<Map<String, Object>> getChatList(int goodsPk){
        List<ChatDto> chatList = auctionMapper.getChatList(goodsPk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(ChatDto chatDto : chatList){
            Map<String, Object> map = new HashMap<>();
            
            UserDto userDto = auctionMapper.getUserInfoByPk(chatDto.getUser_pk());
        
            map.put("chatDto", chatDto);
            map.put("userDto", userDto);
        
            list.add(map);
        }

        return list;
    }

    public Map<String, Object> getBidList(int goodsPk){
        Map<String, Object> map = new HashMap<>();

        List<BidDto> bidList = auctionMapper.getBidListByGoodsPk(goodsPk);
        List<Map<String, Object>> list = new ArrayList<>();

        int highestBidPrice = auctionMapper.getHighestBidPrice(goodsPk);

        for(BidDto bidDto : bidList){
            Map<String, Object> bidMap = new HashMap<>();

            int userPk = bidDto.getUser_pk();
            UserDto userDto = auctionMapper.getUserInfoByPk(userPk);

            bidMap.put("userDto", userDto);
            bidMap.put("bidDto", bidDto);

            list.add(bidMap);
        }

        map.put("bidList", list);
        map.put("highestBidPrice", highestBidPrice);

        return map;
    }

    public Map<String, Object> registerBid(BidDto bidDto){
        Map<String, Object> map = new HashMap<>();
        
        int highestBidPrice = auctionMapper.getHighestBidPrice(bidDto.getGoods_pk());
        int bidPrice = bidDto.getBid_price();

        boolean isBid = false;
        boolean isBidEnd = false;

        AuctionGoodsDto auctionGoodsDto = auctionMapper.getGoodsDto(bidDto.getGoods_pk());
        LocalDateTime expiryDate = auctionGoodsDto.getExpiry_date();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, expiryDate);

        if(duration.isNegative()){

            isBidEnd = true;
        }else{

            if(!(bidPrice <= highestBidPrice)){
                auctionMapper.insertBidDto(bidDto);
                isBid = true;

                if(duration.toSeconds() < 60){
                    LocalDateTime newExpiryDate = expiryDate.plusSeconds(60);
                    auctionGoodsDto.setExpiry_date(newExpiryDate);
                    auctionMapper.updateGoodsExpiryDatePlusMinutes(auctionGoodsDto);
                }
            }
        }

        map.put("isBid", isBid);
        map.put("isBidEnd", isBidEnd);

        return map;
    }

    public List<Map<String, Object>> getAppendChatList(ChatDto chatDto){
        List<ChatDto> chatList = auctionMapper.getAppendChatList(chatDto);
        List<Map<String, Object>> list = new ArrayList<>();

        for(ChatDto e : chatList){
            Map<String, Object> map = new HashMap<>();
            
            UserDto userDto = auctionMapper.getUserInfoByPk(e.getUser_pk());
        
            map.put("chatDto", e);
            map.put("userDto", userDto);
        
            list.add(map);
        }

        return list;
    }


    public List<Map<String, Object>> getMyGoddsBidHistory(int userPk){
        List<AuctionGoodsDto> goodsList = auctionMapper.getMyBidGoodsList(userPk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(AuctionGoodsDto e : goodsList){
            Map<String, Object> map = new HashMap<>();

            boolean isAuctionEnd = false;
            boolean isSuccessfulBid  = false;

            BidDto bidDto = new BidDto();
            bidDto.setUser_pk(userPk);
            bidDto.setGoods_pk(e.getPk());

            BidDto myHighestBidDto = auctionMapper.getMyHighestBidByGoods(bidDto);            
            BidDto highestBidDto = auctionMapper.getHighestBidByGoods(bidDto);            

            LocalDateTime expiryDate = e.getExpiry_date();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, expiryDate);

            if(duration.isNegative()){
                isAuctionEnd = true;

                if(myHighestBidDto.getPk() == highestBidDto.getPk()){
                    isSuccessfulBid = true;
                    int countPayment = auctionMapper.getCountPayment(myHighestBidDto.getPk());
                    boolean isPayment = false;

                    if(countPayment == 0){
                        map.put("isPayment", isPayment);
                    }else{
                        isPayment = true;
                        map.put("isPayment", isPayment);
                    }
                }
            }

            map.put("auctionGoodsDto", e);
            map.put("myHighestBid", myHighestBidDto);
            map.put("highestBid", highestBidDto);
            map.put("isAuctionEnd", isAuctionEnd);
            map.put("isSuccessfulBid", isSuccessfulBid);

            list.add(map);
        }

        return list;
    }

    public List<Map<String, Object>> getMyBidList(BidDto bidDto){

        List<BidDto> bidList = auctionMapper.getMyBidList(bidDto);
        List<Map<String, Object>> list = new ArrayList<>();

        for(BidDto e : bidList){
            Map<String, Object> map = new HashMap<>();

            UserDto userDto = auctionMapper.getUserInfoByPk(e.getUser_pk());

            map.put("userDto", userDto);
            map.put("bidDto", e);

            list.add(map);
        }

        return list;
    }

    public Map<String, Object> getPaymentInfo(int bidPk){
        BidDto bidDto = auctionMapper.getBidDto(bidPk);
        
        AuctionGoodsDto goodsDto = auctionMapper.getGoodsDto(bidDto.getGoods_pk());

        Map<String, Object> map = new HashMap<>();

        map.put("bidDto", bidDto);
        map.put("goodsDto", goodsDto);

        return map;
    }



    public void insertPayment(PaymentDto paymentDto){
        auctionMapper.insertPayment(paymentDto);
    }


    public List<Map<String, Object>> getMyPayment(int userPk){
        List<AuctionGoodsDto> goodsList = auctionMapper.getMyBidGoodsList(userPk);
        List<Map<String, Object>> list = new ArrayList<>();

        for(AuctionGoodsDto e : goodsList){
            Map<String, Object> map = new HashMap<>();


            BidDto bidDto = new BidDto();
            bidDto.setUser_pk(userPk);
            bidDto.setGoods_pk(e.getPk());

            BidDto myHighestBidDto = auctionMapper.getMyHighestBidByGoods(bidDto);            
            BidDto highestBidDto = auctionMapper.getHighestBidByGoods(bidDto);            

            LocalDateTime expiryDate = e.getExpiry_date();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, expiryDate);

            if(duration.isNegative()){

                if(myHighestBidDto.getPk() == highestBidDto.getPk()){
                    int countPayment = auctionMapper.getCountPayment(myHighestBidDto.getPk());


                    if(countPayment != 0){

                        boolean isDelivery = false;
                        boolean isSuccess = false;


                        PaymentDto paymentDto = auctionMapper.getPaymentDto(myHighestBidDto.getPk());

                        int countDelivery = auctionMapper.getCountDeliveryDto(paymentDto.getPk());
                        
                        if(countDelivery == 0){

                        }else{
                            isDelivery = true;

                            DeliveryDto deliveryDto = auctionMapper.getDeliveryDto(paymentDto.getPk());

                            LocalDateTime deliveryDate = deliveryDto.getCreated_at();

                            if(now.isAfter(deliveryDate.plusDays(1))){
                                isSuccess = true;
                            }
                            map.put("deliveryDto", deliveryDto);
                            
                        }

                        map.put("auctionGoodsDto", e);
                        map.put("paymentDto", paymentDto);
                        map.put("isDelivery", isDelivery);
                        map.put("isSuccess", isSuccess);
            
                        list.add(map);
    
                    }

                }
            }
        }

        return list;
    }

   public List<Map<String, Object>> getMySale(int userPk){
        List<AuctionGoodsDto> goodsList = auctionMapper.getSaleGoodsList(userPk);
        List<Map<String, Object>> list = new ArrayList<>();


        String isState = null;
        for(AuctionGoodsDto auctionGoodsDto : goodsList){
            Map<String, Object> map = new HashMap<>();

            BidDto bidDto = new BidDto();
            bidDto.setGoods_pk(auctionGoodsDto.getPk());

            BidDto highestBidDto = auctionMapper.getHighestBidByGoods(bidDto);
            int bidPk = highestBidDto.getPk();
            
            UserDto userDto = auctionMapper.getUserInfoByPk(highestBidDto.getUser_pk());
            
            int countPayment = auctionMapper.getCountPayment(bidPk);


            if(countPayment == 0){
                isState = "결제대기";
            }else{

                PaymentDto paymentDto = auctionMapper.getPaymentDto(bidPk);
                int countDelivery = auctionMapper.getCountDeliveryDto(paymentDto.getPk());
    
                map.put("paymentDto", paymentDto);

                if(countDelivery == 0){
                    isState = "결제완료";
                }else{

                    DeliveryDto deliveryDto = auctionMapper.getDeliveryDto(paymentDto.getPk());

                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime deliveryDate = deliveryDto.getCreated_at();

                    if(now.isAfter(deliveryDate.plusDays(1))){
                        isState = "배송완료";
                    }else{
                        isState = "배송중";
                    }

                    map.put("deliveryDto", deliveryDto);
                }

            }
            map.put("auctionGoodsDto", auctionGoodsDto);
            map.put("isState", isState);
            map.put("bidDto", highestBidDto);
            map.put("userDto", userDto);

            list.add(map);
        }

        return list;
    }


    public void insertDelivery(DeliveryDto deliveryDto){

        auctionMapper.insertDelivery(deliveryDto);
    }

    public void toggleLike(WishlistDto wishlistDto){

        int count = auctionMapper.getCountWishlist(wishlistDto);

        if(count == 0){
            auctionMapper.insertWishlist(wishlistDto);
        }else{
            auctionMapper.deleteWishlist(wishlistDto);
        }

    }


    public Map<String, Object> getDogInfo(int userPk){

        int count = auctionMapper.getDogCount(userPk);

        boolean isDogExist = false;

        Map<String, Object> map = new HashMap<>();

        if(count != 0){
            isDogExist = true;
            DogDto dogDto = auctionMapper.getDogInfo(userPk);
            map.put("dogImage", dogDto.getImage());
            map.put("dogName", dogDto.getName());
        }else{
            map.put("dogImage", null);
            map.put("dogName", null);
        }

        
        map.put("isDogExist", isDogExist);

        return map;
    }
    

}






