package com.psychopath.dogstalking.commons.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.commons.service.UserServiceImpl;
import com.psychopath.dogstalking.dto.KakaoUserDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.funding.dto.FundingProductDto;
import com.psychopath.dogstalking.funding.service.FundingServiceImpl;
import com.psychopath.dogstalking.mstar.service.MstarServiceImpl;
import com.psychopath.dogstalking.trade.service.TradeServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commons/*")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private FundingServiceImpl fundingService;

	@Autowired
	private ClubServiceImpl clubService;

	@Autowired
	private TradeServiceImpl tradeService;

	@Autowired
	private MstarServiceImpl mstarService;


	@RequestMapping("loginPage")
	public String loginPage() {
		return "commons/loginPage";
	}

	@RequestMapping("loginProcess")
	public String loginProcess(HttpSession session, UserDto params) {
		UserDto sessionUser = userService.getUserInfoByUserIdAndPassword(params);

		if (sessionUser == null) {
			// 인증 실패
			return "redirect:./loginPage";
		} else {
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
	public String mainPage(Model model, HttpSession session,KakaoUserDto kakaoUserDto) {

		List<FundingProductDto> selectFundingMainList = fundingService.selectFundingMainList();
        
		int sizeList = selectFundingMainList.size();
	
		model.addAttribute("sizeList", sizeList);
		model.addAttribute("selectFundingMainList", selectFundingMainList);



		List<Map<String, Object>> selectMstarUserMainPage = userService.selectMstarUserMainPage();
		model.addAttribute("selectMstarUserMainPage", selectMstarUserMainPage);

		List<Map<String, Object>> clublist = clubService.selectClubList();
        model.addAttribute("clublist", clublist);

		UserDto userDto = (UserDto) session.getAttribute("sessionUser");
		if(userDto != null){

			if(clubService.isClubMember(userDto.getUser_pk()) != 0) {
				int user_pk = userDto.getUser_pk();

				String path = userService.getProfilePhotoPath(user_pk).getProfile_photo();
				//.out.println();
				model.addAttribute("path", path);
		
				
				int pk=userDto.getUser_pk();
				int club_pk=clubService.selectClubPK(pk);
				
				List<Map<String, Object>> selectFreeboardMainPage = clubService.selectFreeboardMainPage(club_pk);
				model.addAttribute("selectFreeboardMainPage", selectFreeboardMainPage);
			}

		}




		return "commons/mainPage";
	}

}
