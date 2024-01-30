package com.psychopath.dogstalking.commons.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ResponseEntity<String> processKakaoInfo(@RequestBody KakaoUserDto kakaoUserDto, RedirectAttributes redirectAttributes) {
        try {
            String kakaoUserIdString = kakaoUserDto.getKakaoUserId();
            long kakaoUserId = Long.parseLong(kakaoUserIdString);

            Map<String, Object> kakaoUser = userService.getKakaoUser(kakaoUserId);

            if (kakaoUser != null) {
                KakaoUserDto kakaoUserDtos = new KakaoUserDto();
                kakaoUserDtos.setKakaoUserId(String.valueOf(kakaoUserId));
                kakaoUserDtos.setNickname((String) kakaoUser.get("nickname"));
                kakaoUserDtos.setProfileImage((String) kakaoUser.get("profile_image_url"));

                redirectAttributes.addFlashAttribute("kakaoUser", kakaoUserDtos);
                return ResponseEntity.ok("login success");
            } else {
                System.out.println("회원가입 시키기");
            
                userService.saveKakaoUser(kakaoUserDto);

                kakaoUserIdString = kakaoUserDto.getKakaoUserId();
                kakaoUserId = Long.parseLong(kakaoUserIdString);

                kakaoUser = userService.getKakaoUser(kakaoUserId);

                System.out.println("kakaoUser :" + kakaoUser);
                
                UserDto userDto = new UserDto();
                userDto.setUserid(kakaoUserIdString);
                userDto.setName((String) kakaoUser.get("nickname"));
                userDto.setImage((String) kakaoUser.get("profile_image_url"));
                userService.insertKUser(userDto);

                return ResponseEntity.ok("registration success");
            }
        } catch (Exception e) {
            // 예외 발생 시 에러 응답
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
}
