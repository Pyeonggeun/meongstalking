package com.psychopath.dogstalking.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.club.dto.CommentDto;
import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/club/*")
public class ClubRestController {

    @Autowired
    private ClubServiceImpl clubService;

    @RequestMapping("loadSignupModal")
	public RestResponseDto companyListModal(int pk) {

		RestResponseDto response = new RestResponseDto();
		response.setResult("success");

		response.setData(clubService.getloadSignupModal(pk));

		return response;
	}

	@RequestMapping("writeComment")
	public RestResponseDto writeComment(HttpSession session, CommentDto params) {
		RestResponseDto restResponseDto = new RestResponseDto();
		
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
      	params.setUser_pk(userDto.getUser_pk());

        clubService.writeComment(params);

		restResponseDto.setResult("success");
		return restResponseDto;
	}

	@RequestMapping("getMyId")
	public RestResponseDto getMyId(HttpSession session) {
		RestResponseDto restResponseDto = new RestResponseDto();
		System.out.println("getMyId 진입");
		UserDto sessionUser = (UserDto)session.getAttribute("sessionUser");
		
		restResponseDto.setResult("success");
		
		if(sessionUser != null) {
			restResponseDto.setData(sessionUser.getUser_pk());
		}
		
		return restResponseDto;
	}

	@RequestMapping("getCommentList")
	public RestResponseDto getCommentList(@RequestParam("clubfreeboard_pk")int articleId) {
		RestResponseDto restResponseDto = new RestResponseDto();
		
		restResponseDto.setData(clubService.getCommentList(articleId));
		
		restResponseDto.setResult("success");
		return restResponseDto;
	}

}
