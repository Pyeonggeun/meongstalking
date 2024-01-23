package com.psychopath.dogstalking.club.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusCategoryDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubServiceImpl clubService;

    @RequestMapping("clubHomePage")
    public String clubHomePage(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);

        List<Map<String, Object>> clublist = clubService.selectClubList();
        model.addAttribute("clublist", clublist);

        // Map<String, Object> showclubpk = clubService.showclubpk(1);
        // model.addAttribute("showclubpk", showclubpk);

        return "club/clubHomePage";
    }

    @RequestMapping("freeBoardPage")
    public String freeBoardPage(HttpSession session, Model model, ClubDto clubDto) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        // Map<String, Object> clubTF =
        // clubService.applyClubUserTF(userDto.getUser_pk());
        // model.addAttribute("clubTF", clubTF);

        int useid = userDto.getUser_pk();
        int pk = clubService.selectClubPK(useid);

        List<Map<String, Object>> freeBoardList = clubService.selectFreeBoardAll(pk);
        model.addAttribute("freeBoardList", freeBoardList);

        // System.out.println(freeBoardList);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
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
    public String freeBoardReadPage(Model model, int clubfreeboard_pk) {
        model.addAttribute("clubfreeboard_pk", clubfreeboard_pk);

        return "club/freeBoardReadPage";
    }

    @RequestMapping("createClubPage")
    public String createClubPage() {
        return "club/createClubPage";
    }

    @RequestMapping("clubLeaderPage")
    public String clubLeaderPage(HttpSession session, Model model, ClubDto clubDto) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "club/clubLeaderPage";
    }

    @RequestMapping("clubSignupPage")
    public String clubSignupPage(HttpSession session, Model model, ClubDto clubDto) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);

        int pk = (int) clubTF.get("club_pk");
        List<Map<String, Object>> applyList = clubService.selectApplyList(pk);

        if (applyList != null && !applyList.isEmpty()) {
            model.addAttribute("applyList", applyList);
            return "club/clubSignupPage";
        } else {
            return "club/clubNoSignupPage";
        }
    }

    @RequestMapping("clubNoSignupPage")
    public String clubNoSignupPage() {
        return "club/clubNoSignupPage";
    }

    @RequestMapping("clubListPage")
    public String clubListPage(Model model) {
        List<Map<String, Object>> clublist = clubService.selectClubList();
        model.addAttribute("clublist", clublist);

        Map<String, Object> showclubpk = clubService.showclubpk(1);
        model.addAttribute("showclubpk", showclubpk);

        return "club/clubListPage";
    }

    @RequestMapping("createclubProcess")
    public String createclubProcess(HttpSession session, ClubDto clubDto, ClubStatusLogDto clubStatusLogDto,
            ClubUserDto clubUserDto, Model model, ClubUserRanklogDto clubUserRanklogDto) {

        clubService.insertClub(clubDto);

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        int clubpk = clubService.checka();
        if (clubpk == 0) {
            clubpk = 1;
        }
        int checkb = clubService.checkb();

        clubUserDto.setClub_user_pk(clubpk + 1);
        clubUserDto.setUser_pk(userDto.getUser_pk());
        clubUserDto.setClub_pk(checkb);
        clubService.insertClubUser(clubUserDto);

        clubUserRanklogDto.setClub_user_pk(userDto.getUser_pk());
        clubUserRanklogDto.setClubuserranklogcategory_pk(1);
        clubService.insertLeader(clubUserRanklogDto);

        int pk = clubUserDto.getUser_pk();
        clubStatusLogDto.setClub_user_pk(pk);
        clubStatusLogDto.setClubstatuslog_pk(1);
        clubStatusLogDto.setClubcategory_pk(1);
        clubService.insertUserStatusLog(clubStatusLogDto);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "redirect:./clubHomePage";
    }

    @RequestMapping("clubHomeProcess")
    public String clubHomeProcess(@RequestParam("club_pk") int clubPk, Model model, HttpSession session) {

        Map<String, Object> showclubpk = clubService.showclubpk(clubPk);
        model.addAttribute("showclubpk", showclubpk);
        return "club/clubListPage";
    }

    @RequestMapping("createUserProcess")
    public String createUserProcess(@RequestParam("club_pk") String clubPkString, HttpSession session, ClubDto clubDto,
            ClubUserDto clubUserDto, ClubStatusLogDto clubStatusLogDto, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        int clubPk = Integer.parseInt(clubPkString);
        clubUserDto.setClub_pk(clubPk);
        clubUserDto.setUser_pk(userDto.getUser_pk());

        clubService.insertClubUsers(clubUserDto);

        int pk = clubUserDto.getUser_pk();
        clubStatusLogDto.setClub_user_pk(pk);
        clubStatusLogDto.setClubcategory_pk(2);
        clubService.insertUserStatusLog(clubStatusLogDto);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "redirect:./clubListPage";
    }

    @RequestMapping("approve")
    public String approve(@RequestParam("user_pk") int userPk, ClubUserDto clubUserDto,
            ClubStatusLogDto clubStatusLogDto) {

        clubStatusLogDto.setClub_user_pk(userPk);
        clubService.updateApplyClub(clubStatusLogDto);

        return "redirect:./clubSignupPage";
    }

    @RequestMapping("reject")
    public String reject(@RequestParam("user_pk") int userPk, ClubUserDto clubUserDto,
            ClubStatusLogDto clubStatusLogDto) {

        clubStatusLogDto.setClub_user_pk(userPk);
        clubService.updatenotApplyClub(clubStatusLogDto);

        return "redirect:./clubSignupPage";
    }

    @RequestMapping("clubChangeMainPage")
    public String clubChangeMainPage(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "club/clubChangeMainPage";
    }

    @RequestMapping("updateClubProcess")
    public String updateClubProcess(ClubDto clubDto) {
        clubService.updateClub(clubDto);
        return "redirect:./clubHomePage";
    }

    @RequestMapping("clubMemberPage")
    public String clubMemberPage(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        System.out.println("User PK: " + userDto.getUser_pk());
        model.addAttribute("user_pk", userDto.getUser_pk());

        int clubPk = clubService.selectClubPK(userDto.getUser_pk());
        List<Map<String, Object>> memberlist = clubService.selectMember(clubPk);
        model.addAttribute("memberlist", memberlist);

        return "club/clubMemberPage";
    }

    @RequestMapping("clubWithdrawalProcess")
    public String clubWithdrawalProcess(@RequestParam("user_pk") int user_pk, HttpSession session,
            ClubUserDto clubUserDto, ClubStatusLogDto clubStatusLogDto) {
        clubStatusLogDto.setClub_user_pk(user_pk);
        clubService.withdrawalClubUser(clubStatusLogDto);

        return "redirect:./clubMemberPage";
    }

}
