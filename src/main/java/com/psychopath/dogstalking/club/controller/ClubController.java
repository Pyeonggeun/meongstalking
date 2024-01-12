package com.psychopath.dogstalking.club.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubServiceImpl clubService;

    @RequestMapping("clubHomePage")
    public String clubHomePage(Model model,HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "club/clubHomePage";
    }

    @RequestMapping("freeBoardPage")
    public String freeBoardPage(Model model) {
        List<Map<String, Object>> freeBoardList = clubService.selectFreeBoardAll();
        model.addAttribute("freeBoardList", freeBoardList);
        return "club/freeBoardPage";
    }

    @RequestMapping("freeBoardWritePage")
    public String freeBoardWritePage(HttpSession session) {

        return "club/freeBoardWritePage";
    }

    @RequestMapping("boardwriteProcess")
    public String boardwriteProcess(HttpSession session, ClubFreeBoardDto params) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        params.setUser_pk(userDto.getUser_pk());
        params.setClub_pk(1);
        clubService.writeArticle(params);

        return "redirect:./freeBoardPage";
    }

    @RequestMapping("freeBoardReadPage")
    public String freeBoardReadPage(HttpSession session, Model model, int clubfreeboard_pk) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        model.addAttribute("clubfreeboard_pk", clubfreeboard_pk);

        return "club/freeBoardReadPage";
    }
    
    @RequestMapping("createClubPage")
    public String createClubPage() {
        return "club/createClubPage";
    }

    @RequestMapping("createclubProcess")
    public String createclubProcess(HttpSession session, ClubDto clubDto, ClubUserDto clubUserDto) {
        
        
        clubService.insertClub(clubDto);

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        System.out.println("확인하기");
        System.out.println(clubDto);
        

        clubUserDto.setClub_pk(clubDto.getClub_pk());
        clubUserDto.setUser_pk(userDto.getUser_pk());
        clubService.insertClubUser(clubUserDto);

        return "redirect:./clubHomePage";
    }

}
