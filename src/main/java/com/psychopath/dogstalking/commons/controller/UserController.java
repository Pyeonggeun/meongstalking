package com.psychopath.dogstalking.commons.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.commons.service.UserServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commons/*")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    @RequestMapping("loginPage")
    public String loginPage(){
        return "commons/loginPage";
    }

    @RequestMapping("loginProcess")
    public String loginProcess(HttpSession session, UserDto params){
        UserDto sessionUser = userService.getUserInfoByUserIdAndPassword(params);
  
        if(sessionUser == null) {
			// 인증 실패
			return "redirect:./loginPage";
		}else {
			// 인증 성공
			session.setAttribute("sessionUser", sessionUser);
            System.out.println("-------------");
            System.out.println(sessionUser.getName());
            System.out.println("-------------");
			return "redirect:./mainPage";
		}
    }

    @RequestMapping("registerPage")
	public String registerPage() {
		
		return "commons/registerPage";
	}

    @RequestMapping("registerProcess")
	public String registerProcess(UserDto params) {
		userService.register_user(params);

		return "redirect:./loginPage";
	} 

    @RequestMapping("mainPage")
	public String mainPage() {
		
		return "commons/mainPage";
	}
}
