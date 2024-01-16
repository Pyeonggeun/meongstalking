package com.psychopath.dogstalking.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.CarePriceDto;
import com.psychopath.dogstalking.trade.dto.ChatMessageDto;
import com.psychopath.dogstalking.trade.dto.RestResponseTradeDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;
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
	public RestResponseTradeDto registerTradeArticleFirst(TradeArticleDto params) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
        
        int articlePk = tradeService.registerTradeArticleFirst(params);
		
		restResponseDto.setData(articlePk);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("registerTradeArticleSecond")
	public RestResponseTradeDto registerTradeArticleSecond(TradeArticleDto params, int price) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		CarePriceDto carePriceDto = new CarePriceDto();

		tradeService.registerArticleSecond(params);

		int articlePk = params.getPk();
		carePriceDto.setArticle_pk(articlePk);
		carePriceDto.setPrice(price);
		tradeService.registerCarePrice(carePriceDto);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    




	@RequestMapping("registerCareDog")
	public RestResponseTradeDto registerCareDog(CareDogDto params) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		tradeService.registerCareDog(params);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	@RequestMapping("getArticleList")
	public RestResponseTradeDto getArticleList() {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		restResponseDto.setData(tradeService.getArticleList());
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
