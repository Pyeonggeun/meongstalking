package com.psychopath.dogstalking.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String mainPage(Model model, HttpSession session) {
		UserDto userDto = (UserDto)session.getAttribute("sessionUser");
		int user_pk = userDto.getUser_pk();
		
		String path = userService.getProfilePhotoPath(user_pk).getProfile_photo();
		System.out.println();
		model.addAttribute("path", path);

		return "commons/mainPage";
	}
}
