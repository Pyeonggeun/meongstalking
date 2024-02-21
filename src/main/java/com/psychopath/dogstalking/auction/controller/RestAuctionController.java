package com.psychopath.dogstalking.auction.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.auction.dto.AuctionGoodsDto;
import com.psychopath.dogstalking.auction.dto.AuctionImageDto;
import com.psychopath.dogstalking.auction.dto.BidDto;
import com.psychopath.dogstalking.auction.dto.ChatDto;
import com.psychopath.dogstalking.auction.dto.DeliveryDto;
import com.psychopath.dogstalking.auction.dto.PaymentDto;
import com.psychopath.dogstalking.auction.dto.WishlistDto;
import com.psychopath.dogstalking.auction.service.AuctionServiceImpl;
import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.dto.CareImageDto;
import com.psychopath.dogstalking.trade.dto.RestResponseTradeDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auction/*")
public class RestAuctionController {

    @Autowired
    private AuctionServiceImpl auctionService;

    @RequestMapping("getCategory")
    public RestResponseDto getCategory() {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getCategory());
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    


    @RequestMapping("registerCategory")
    public RestResponseDto registerCategory(HttpSession session, int category_pk){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        AuctionGoodsDto auctionGoodsDto = new AuctionGoodsDto();

        auctionGoodsDto.setCategory_pk(category_pk);

        session.setAttribute("sessionAuctionGoodsDto", auctionGoodsDto);

        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("previewImage")
    public RestResponseDto previewImage(@RequestParam("imageFiles") MultipartFile[] imageFiles, HttpSession session){
       
        RestResponseDto restResponseDto = new RestResponseDto();
		//이미지
		List<AuctionImageDto> auctionImageDtoList = new ArrayList<>(); 
		
		// 파일 저장 로직
		if(imageFiles != null) {
			for(MultipartFile multipartFile : imageFiles) {
				if(multipartFile.isEmpty()) {
					continue;
				}

				String rootPath = "C:/uploadFiles/";
				
				// 날짜별 폴더 생성.
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
				String todayPath = sdf.format(new Date());
				
				File todayFolderForCreate = new File(rootPath + todayPath);
				
				if(!todayFolderForCreate.exists()) {
					todayFolderForCreate.mkdirs();
				}
				
				String originalFileName = multipartFile.getOriginalFilename();

				//파일명 충돌 회피 - 랜덤, 시간 조합
				String uuid = UUID.randomUUID().toString();
				long currentTime = System.currentTimeMillis();
				String fileName = uuid + "_" + currentTime;
				
				// 확장자 추출
				String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
				fileName += ext;
				
				try {
					multipartFile.transferTo(new File(rootPath + todayPath + fileName));					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				AuctionImageDto auctionImageDto = new AuctionImageDto();
				auctionImageDto.setImage_link(todayPath + fileName);

				auctionImageDtoList.add(auctionImageDto);
				
			}
		}

		List<AuctionImageDto> sessionCareDogImageList = (List<AuctionImageDto>)session.getAttribute("auctionImageDtoList");

		if(sessionCareDogImageList == null){
			session.setAttribute("auctionImageDtoList", auctionImageDtoList);
			restResponseDto.setData(auctionImageDtoList);
		}else{
			List<AuctionImageDto> list = new ArrayList<>();

			for(AuctionImageDto auctionImageDto : sessionCareDogImageList){
				list.add(auctionImageDto);
			}
			for(AuctionImageDto auctionImageDto : auctionImageDtoList){
				list.add(auctionImageDto);
			}
			session.setAttribute("auctionImageDtoList", list);
			restResponseDto.setData(list);
		}


		restResponseDto.setResult("success");
		
		return restResponseDto;
	}      
	

    @RequestMapping("registerGoodsInfo")
    public RestResponseDto registerGoodsInfo(String title, String content, String[] images, HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        AuctionGoodsDto auctionGoodsDto =  (AuctionGoodsDto)session.getAttribute("sessionAuctionGoodsDto");

        auctionGoodsDto.setTitle(title);
        auctionGoodsDto.setContent(content);

        session.setAttribute("sessionAuctionGoodsDto", auctionGoodsDto);

        session.setAttribute("sessionGoodsImageList", images);

        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }    
    
    @RequestMapping("registerGoods")
    public RestResponseDto registerGoods(
		@RequestParam("commencement_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime commencementDate, 
		@RequestParam("expiry_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryDate, 
		int starting_price, 
		int bin_price,  
		HttpSession session){

        RestResponseDto restResponseDto = new RestResponseDto();
        
        AuctionGoodsDto auctionGoodsDto =  (AuctionGoodsDto)session.getAttribute("sessionAuctionGoodsDto");
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

		String[] images = (String[])session.getAttribute("sessionGoodsImageList");


        auctionGoodsDto.setCommencement_date(commencementDate);
        auctionGoodsDto.setExpiry_date(expiryDate);
        auctionGoodsDto.setStarting_price(starting_price);
        auctionGoodsDto.setBin_price(bin_price);
        auctionGoodsDto.setUser_pk(sessionUserPk);

        auctionService.registerGoods(auctionGoodsDto, images);

        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

	@RequestMapping("getGoodsListByCategoryPk")
	public RestResponseDto getGoodsListByCategoryPk(int categoryPk, HttpSession session){

        RestResponseDto restResponseDto = new RestResponseDto();

		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

        restResponseDto.setData(auctionService.getGoodsListByCategoryPk(categoryPk, sessionUserPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;

	}


	@RequestMapping("getGoodsDetailInfo")
    public RestResponseDto getGoodsDetailInfo(int goodsPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getGoodsDetailInfo(goodsPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    


	@RequestMapping("registerChatMessage")
	public RestResponseDto registerChatMessage(ChatDto params, HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

		params.setUser_pk(sessionUserPk);

		auctionService.registerChatMessage(params);

        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

	@RequestMapping("getChatList")
    public RestResponseDto getChatList(int goodsPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
		
        restResponseDto.setData(auctionService.getChatList(goodsPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

	@RequestMapping("getBidList")
	public RestResponseDto getBidList(int goodsPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getBidList(goodsPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

	@RequestMapping("registerBid")
    public RestResponseDto registerBid(BidDto prams) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.registerBid(prams));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

	@RequestMapping("getGoodsInfo")
	public RestResponseDto getGoodsInfo(int goodsPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getGoodsInfo(goodsPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

	@RequestMapping("getAppendChatList")
	public RestResponseDto getAppendChatList(ChatDto chatDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getAppendChatList(chatDto));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    


    @RequestMapping("getMyGoodsBidHistory")
    public RestResponseDto getMyGoodsBidHistory(int userPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getMyGoddsBidHistory(userPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("getMyBidList")
    public RestResponseDto getMyBidList(int userPk, int goodsPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        BidDto bidDto = new BidDto();
        bidDto.setGoods_pk(goodsPk);
        bidDto.setUser_pk(userPk);

        restResponseDto.setData(auctionService.getMyBidList(bidDto));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("getPaymentInfo")
    public RestResponseDto getPaymentInfo(int bidPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getPaymentInfo(bidPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("insertPayment")
    public RestResponseDto insertPayment(PaymentDto paymentDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        auctionService.insertPayment(paymentDto);

        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("getMyPayment")
    public RestResponseDto getMyPayment(int userPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getMyPayment(userPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("getMySale")
    public RestResponseDto getMySale(int userPk) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.getMySale(userPk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}   
    
    @RequestMapping("insertDelivery")
    public RestResponseDto insertDelivery(DeliveryDto deliveryDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        auctionService.insertDelivery(deliveryDto);
        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    @RequestMapping("toggleLike")
    public RestResponseDto toggleLike(WishlistDto wishlistDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        auctionService.toggleLike(wishlistDto);
        
        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}   

    @RequestMapping("mainPageGoodsList")
    public RestResponseDto mainPageGoodsList() {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(auctionService.mainPageGoodsList());
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    

    public RestResponseDto template() {
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(null);
        restResponseDto.setResult("success");
        
        return restResponseDto;
	}    
}
