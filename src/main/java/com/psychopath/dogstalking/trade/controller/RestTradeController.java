package com.psychopath.dogstalking.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

import com.psychopath.dogstalking.trade.dto.CareDogDto;
import com.psychopath.dogstalking.trade.dto.RestResponseTradeDto;
import com.psychopath.dogstalking.trade.dto.TradeArticleDto;
import com.psychopath.dogstalking.trade.service.TradeServiceImpl;

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

	@RequestMapping("registerCareDog")
	public RestResponseTradeDto registerCareDog(CareDogDto params) {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		tradeService.registerCareDog(params);

		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    

	public RestResponseTradeDto template() {
		RestResponseTradeDto restResponseDto = new RestResponseTradeDto();
		
		restResponseDto.setData(null);
		restResponseDto.setResult("success");
		
		return restResponseDto;
	}    


}
