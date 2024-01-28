package com.psychopath.dogstalking.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.commons.service.UserServiceImpl;
import com.psychopath.dogstalking.dto.KakaoUserDto;
import com.psychopath.dogstalking.dto.UserDto;

@RestController
@RequestMapping("/commons")
public class UserRestController {
     @Autowired
    private UserServiceImpl userService;
    
	//카카오 
	@PostMapping("/kakaoProcess")
	public String processKakaoInfo(@RequestBody  KakaoUserDto kakaoUserDto) {
        try {
            UserDto kakaoUser = userService.getKakaoUser(kakaoUserDto.getCode());
            userService.saveKakaoUser(kakaoUser);
            return "success";
        } catch (Exception e) {
            // 로그에 오류 기록
            e.printStackTrace();
            return "error";
        }
    }
}
