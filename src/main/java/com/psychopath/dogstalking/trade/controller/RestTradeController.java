package com.psychopath.dogstalking.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.UUID;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.CareImageDto;
import com.psychopath.dogstalking.trade.dto.CarePriceDto;
import com.psychopath.dogstalking.trade.dto.ChatMessageDto;
import com.psychopath.dogstalking.trade.dto.RestResponseTradeDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;
import com.psychopath.dogstalking.trade.dto.WishListDto;
import com.psychopath.dogstalking.trade.service.TradeServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/trade/*")
public class RestTradeController {

    @Autowired
    private TradeServiceImpl tradeService;

    @RequestMapping("getDogList")
	public RestResponseTradeDto getDogList(@RequestParam("userPk")int userPk) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
 

		restResponseDto.setData(tradeService.getDogList(userPk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

    @RequestMapping("registerTradeArticleFirst")
	public RestResponseTradeDto registerTradeArticleFirst(TradeArticleDto params, int[] careDogList, HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();

		session.setAttribute("registerArticleDto", params);
		session.setAttribute("careDogList", careDogList);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("registerTradeArticleSecond")
	public RestResponseTradeDto registerTradeArticleSecond(TradeArticleDto params, int price, HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		CarePriceDto carePriceDto = new CarePriceDto();

		TradeArticleDto articleDto = (TradeArticleDto)session.getAttribute("registerArticleDto");
		articleDto.setTitle(params.getTitle());
		articleDto.setContent(params.getTitle());

		int[] careDog = (int[])session.getAttribute("careDogList");


		tradeService.registerCareArticle(articleDto, price, careDog);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    


	@RequestMapping("registerImage")
	public RestResponseTradeDto registerImage(@RequestParam("imageFiles") MultipartFile[] imageFiles, HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		//이미지
		List<CareImageDto> careImageDtoList = new ArrayList<>(); 
		
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
				
				CareImageDto careImageDto = new CareImageDto();
				careImageDto.setImage(todayPath + fileName);
				System.out.println("------------------------------");
				System.out.println(todayPath);
				System.out.println("------------------------------");

				careImageDtoList.add(careImageDto);
				
			}
		}
		List<CareImageDto> sessionCareDogImageList = (List<CareImageDto>)session.getAttribute("careImageDtoList");

		if(sessionCareDogImageList == null){
			session.setAttribute("careImageDtoList", careImageDtoList);
			System.out.println("-------------------");
			System.out.println("null");
			System.out.println("-------------------");
			restResponseDto.setData(careImageDtoList);
		}else{
			List<CareImageDto> list = new ArrayList<>();

			for(CareImageDto careImageDto : sessionCareDogImageList){
				list.add(careImageDto);
			}
			for(CareImageDto careImageDto : careImageDtoList){
				list.add(careImageDto);
			}
			session.setAttribute("careImageDtoList", list);
			restResponseDto.setData(list);
			System.out.println("-------------------");
			System.out.println("null 아님");
			System.out.println("-------------------");
		}


		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    



	@RequestMapping("getArticleList")
	public RestResponseTradeDto getArticleList(HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

		restResponseDto.setData(tradeService.getArticleList(sessionUserPk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("getArticleInfo")
	public RestResponseTradeDto getArticleInfo(int articlePk) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		restResponseDto.setData(tradeService.getArticleDetail(articlePk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("createChatRoom")
	public RestResponseTradeDto createChatRoom(int articlePk, int senderPk) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();

		restResponseDto.setData(tradeService.createChatRoom(articlePk, senderPk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("registerChatMessage")
	public RestResponseTradeDto registerChatMessage(ChatMessageDto params, HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();

		UserDto userDto = (UserDto)session.getAttribute("sessionUser");

		params.setUser_pk(userDto.getUser_pk());

		tradeService.registerChatMessage(params);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("getChatRoomList")
	public RestResponseTradeDto getChatRoomList(int userPk) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		restResponseDto.setData(tradeService.getChatRoomList(userPk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("getChatMessageList")
	public RestResponseTradeDto getChatMessageList(int chatRoomPk, HttpSession session) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

		restResponseDto.setData(tradeService.getChatMessage(chatRoomPk, sessionUserPk));
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    


	@RequestMapping("toggleWishList")
	public RestResponseTradeDto toggleWishList(HttpSession session, int articlePk) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		WishListDto wishListDto = new WishListDto();
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int sessionUserPk = userDto.getUser_pk();

		wishListDto.setArticle_pk(articlePk);
		wishListDto.setUser_pk(sessionUserPk);
		tradeService.toggleWishList(wishListDto);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	// @RequestMapping("chatMessageIsRead")
	// public RestResponseTradeDto chatMessageIsRead(int chatMessagePk) {
	// 	RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
	// 	tradeService.chatMessageRead(chatMessagePk);

	// 	restResponseDto.setData(null);
	// 	restResponseDto.setResult("success");
		
	// 	return restResponseDto;
	// }    	




	public RestResponseTradeDto template() {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    


}
