package com.psychopath.dogstalking.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.service.FollowServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/follow")
public class RestFollowController {

    @Autowired
    private FollowServiceImpl followService;

    @RequestMapping("getUserPk")
    public RestResponseDto getUserPk(HttpSession session) {

        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUser");

        restResponseDto.setData(userDto.getUser_pk());
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isFirstTimeMark")
    public RestResponseDto isFirstTimeMark(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.isFirstTimeMark(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }
    
    @RequestMapping("insertMoreInfo")
    public RestResponseDto insertMoreInfo(UserMoreDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertMoreInfo(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("checkWriteMarkDistance")
    public RestResponseDto checkWriteMarkDistance(
        int user_pk, int markingRadius, int markingDate, double latitude, double longitude) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(
            followService.checkWriteMarkDistance(user_pk, markingRadius, markingDate, latitude, longitude));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("checkWriteMarkCount")
    public RestResponseDto checkWriteMarkCount(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.checkWriteMarkCount(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    public RestResponseDto a() {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

}
