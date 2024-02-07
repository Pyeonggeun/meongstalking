package com.psychopath.dogstalking.club.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.club.dto.ClubArticleImgDto;
import com.psychopath.dogstalking.club.dto.ClubDto;
import com.psychopath.dogstalking.club.dto.ClubFreeBoardDto;
import com.psychopath.dogstalking.club.dto.ClubImgBoardDto;
import com.psychopath.dogstalking.club.dto.ClubStatusLogDto;
import com.psychopath.dogstalking.club.dto.ClubUserDto;
import com.psychopath.dogstalking.club.dto.ClubUserRanklogDto;
import com.psychopath.dogstalking.club.service.ClubServiceImpl;
import com.psychopath.dogstalking.dto.UserDto;

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

        Integer memberLank = null;
        memberLank = clubService.selectLeaderLank(userDto.getUser_pk());
        //System.out.println("memberLank : " + memberLank);
        if (memberLank == null || !(memberLank >= 1 && memberLank <= 3)) {
            memberLank = 9;
        }
        // System.out.println("memberLank : " + memberLank);
        model.addAttribute("checkMember", memberLank);

        Integer ApplyStatus = clubService.selectClubCategoryPk(userDto.getUser_pk());

        if (ApplyStatus == null) {
            ApplyStatus = 9;
        }
        model.addAttribute("ApplyStatus", ApplyStatus);

        List<Map<String, Object>> clublist = clubService.selectClubList();
        model.addAttribute("clublist", clublist);

        if (memberLank == 1 || memberLank == 3) {
            return "redirect:./clubListPage";
        } else {
            return "club/clubHomePage";
        }

    }

    @RequestMapping("freeBoardPage")
    public String freeBoardPage(HttpSession session, Model model, ClubDto clubDto) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        int useid = userDto.getUser_pk();
        int pk = clubService.selectClubPK(useid);

        //System.out.println("clubPk: " + pk);

        List<Map<String, Object>> freeBoardList = clubService.selectFreeBoardAll(pk);
        model.addAttribute("freeBoardList", freeBoardList);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);
        return "club/freeBoardPage";
    }

    @RequestMapping("freeBoardWritePage")
    public String freeBoardWritePage(HttpSession session) {

        return "club/freeBoardWritePage";
    }

    @RequestMapping("boardwriteProcess")
    public String boardwriteProcess(HttpSession session, ClubFreeBoardDto params,
            @ModelAttribute("clubFreeBoardDto") ClubFreeBoardDto clubFreeBoardDto,
            @RequestParam("img_file") MultipartFile imageFile) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        if (imageFile != null && !imageFile.isEmpty()) {
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if (!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }

            String originalFileName = imageFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            fileName += ext;

            try {
                imageFile.transferTo(new File(rootPath + todayPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            clubFreeBoardDto.setImg(todayPath + fileName);
        }

        int club_pk = clubService.selectClubPK(userDto.getUser_pk());

        params.setUser_pk(userDto.getUser_pk());
        params.setClub_pk(club_pk);
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

        //System.out.println("applyList: " + applyList);

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
    public String clubListPage(HttpSession session, Model model) {

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        List<Map<String, Object>> clublist = clubService.selectClubList();
        model.addAttribute("clublist", clublist);

        // System.out.println("userDto.getUser_pk(): "+userDto.getUser_pk());

        int clubPk = clubService.selectClubPK(userDto.getUser_pk());

        // System.out.println("clubPk: "+clubPk);

        Map<String, Object> showclubpk = clubService.showclubpk(clubPk);
        model.addAttribute("showclubpk", showclubpk);

        // System.out.println("showclubpk: "+showclubpk);

        Integer memberLank = null;
        memberLank = clubService.selectLeaderLank(userDto.getUser_pk());
        // System.out.println("memberLank : " + memberLank);
        if (memberLank == null || !(memberLank >= 1 && memberLank <= 3)) {
            memberLank = 9;
        }
        // System.out.println("memberLank : " + memberLank);
        model.addAttribute("checkMember", memberLank);

        Integer ApplyStatus = clubService.selectClubCategoryPk(userDto.getUser_pk());
        //System.out.println("ApplyStatus : " + ApplyStatus);
        if (ApplyStatus == null) {
            ApplyStatus = 9;
        }
        model.addAttribute("ApplyStatus", ApplyStatus);

        Map<String, Object> latestPost = clubService.selectLatestPost(clubPk);
        model.addAttribute("latestPost", latestPost);

        List<Map<String, Object>> latestAlbum = clubService.selectLatestAlbum(clubPk);
        model.addAttribute("latestAlbum", latestAlbum);

        List<Map<String, Object>> memberlist = clubService.selectMember(clubPk);
        model.addAttribute("memberlist", memberlist);

       // System.out.println("memberlist: "+memberlist);

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

        int clubPks = clubService.selectClubPK(userDto.getUser_pk());

        clubUserDto.setClub_user_pk(clubpk + 1);
        clubUserDto.setUser_pk(userDto.getUser_pk());
        clubUserDto.setClub_pk(clubPks);
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

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        Map<String, Object> showclubpk = clubService.showclubpk(clubPk);

        if (showclubpk != null) {
            model.addAttribute("showclubpk", showclubpk);
        }
        //System.out.println("showclubpk : " + showclubpk);
        Integer memberLank = null;
        memberLank = clubService.selectLeaderLank(userDto.getUser_pk());
        // System.out.println("memberLank : " + memberLank);
        if (memberLank == null || !(memberLank >= 1 && memberLank <= 3)) {
            memberLank = 9;
        }
        // System.out.println("memberLank : " + memberLank);
        model.addAttribute("checkMember", memberLank);

        Integer ApplyStatus = clubService.selectClubCategoryPk(userDto.getUser_pk());
        System.out.println("ApplyStatus : " + ApplyStatus);
        if (ApplyStatus == null) {
            ApplyStatus = 9;
        }
        model.addAttribute("ApplyStatus", ApplyStatus);

        return "redirect:./clubListPage";
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
            ClubStatusLogDto clubStatusLogDto, ClubUserRanklogDto clubUserRanklogDto, HttpSession session) {

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        clubStatusLogDto.setClub_user_pk(userPk);
        clubService.updateApplyClub(clubStatusLogDto);

        clubUserRanklogDto.setClub_user_pk(userDto.getUser_pk());
        clubUserRanklogDto.setClubuserranklogcategory_pk(3);
        clubService.insertClubUserRank(clubUserRanklogDto);

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
    public String updateClubProcess(@ModelAttribute("clubDto") ClubDto clubDto,
            @RequestParam("img_file") MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty()) {
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if (!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }

            String originalFileName = imageFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime;

            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            fileName += ext;

            try {
                imageFile.transferTo(new File(rootPath + todayPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            clubDto.setImg(todayPath + fileName);
        }

        clubService.updateClub(clubDto);
        return "redirect:./clubHomePage";
    }

    @RequestMapping("clubMemberPage")
    public String clubMemberPage(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        // System.out.println("User PK: " + userDto.getUser_pk());
        model.addAttribute("user_pk", userDto.getUser_pk());

        int leaderRank = clubService.selectLeaderLank(userDto.getUser_pk());
        // System.out.println("leaderRank: " + leaderRank);
        model.addAttribute("leaderRank", leaderRank);

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

    @RequestMapping("clubLeaderChangeProcess")
    public String clubLeaderChangeProcess(@RequestParam("user_pk") int user_pk, HttpSession session,
            ClubUserRanklogDto clubUserRanklogDto) {
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        clubUserRanklogDto.setClub_user_pk(user_pk);
        clubUserRanklogDto.setClubuserranklogcategory_pk(1);
        clubService.updateLeader(clubUserRanklogDto);

        clubUserRanklogDto.setClub_user_pk(userDto.getUser_pk());
        clubUserRanklogDto.setClubuserranklogcategory_pk(3);
        clubService.updateLeader(clubUserRanklogDto);

        return "redirect:./clubMemberPage";
    }

    @RequestMapping("photoAlbumPage")
    public String photoAlbumPage(HttpSession session, Model model) {

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        int useid = userDto.getUser_pk();
        int pk = clubService.selectClubPK(useid);

        //System.out.println("pk: "+pk);

        List<Map<String, Object>> freeBoardList = clubService.selectImgFreeBoardAll(pk);
        model.addAttribute("freeBoardList", freeBoardList);

        //System.out.println("freeBoardList: "+freeBoardList);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);

        return "club/photoAlbumPage";
    }

    @RequestMapping("photoAlbumWritePage")
    public String photoAlbumWritePage(HttpSession session, Model model) {

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");

        int useid = userDto.getUser_pk();
        int pk = clubService.selectClubPK(useid);

        List<Map<String, Object>> freeImgBoardList = clubService.selectImgFreeBoardAll(pk);

       // System.out.println("freeImgBoardList: "+freeImgBoardList);
        model.addAttribute("freeBoardList", freeImgBoardList);

        Map<String, Object> clubTF = clubService.applyClubUserTF(userDto.getUser_pk());
        model.addAttribute("clubTF", clubTF);

        return "club/photoAlbumWritePage";
    }

    @RequestMapping("registerImagePage")
    public String registerImagePage(HttpSession session, ClubImgBoardDto params, MultipartFile[] imageFiles) {
        List<ClubArticleImgDto> articleImageDtoList = new ArrayList<>();

        //System.out.println("registerImagePage 시작");
        // 파일 저장 로직
        if (imageFiles != null) {
            for (MultipartFile multipartFile : imageFiles) {
                if (multipartFile.isEmpty()) {
                    continue;
                }

                String rootPath = "C:/uploadFiles/";

                // 날짜별 폴더 생성.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
                String todayPath = sdf.format(new Date());

                File todayFolderForCreate = new File(rootPath + todayPath);

                if (!todayFolderForCreate.exists()) {
                    todayFolderForCreate.mkdirs();
                }

                String originalFileName = multipartFile.getOriginalFilename();

                // 파일명 충돌 회피 - 랜덤, 시간 조합
                String uuid = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fileName = uuid + "_" + currentTime;

                // 확장자 추출
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName += ext;

                try {
                    multipartFile.transferTo(new File(rootPath + todayPath + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ClubArticleImgDto clubArticleImgDto = new ClubArticleImgDto();
                clubArticleImgDto.setLocation(todayPath + fileName);
                clubArticleImgDto.setOriginal_filename(originalFileName);

                //System.out.println("clubArticleImgDto: "+clubArticleImgDto);

                articleImageDtoList.add(clubArticleImgDto);

                //System.out.println("articleImageDtoList: "+articleImageDtoList);

            }
        }

        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        int clubPk = clubService.selectClubPK(userDto.getUser_pk());
        int userId = userDto.getUser_pk();
        params.setClub_pk(clubPk);
        params.setClub_user_pk(userId);

        //session.setAttribute("articleImageDtoList", articleImageDtoList);
        clubService.writeImgArticle(params, articleImageDtoList);

        return "redirect:./photoAlbumPage";
    }

    @RequestMapping("photoAlbumReadPage")
    public String photoAlbumReadPage(@RequestParam("clubimgboard_pk") int clubimgboard_pk,HttpSession session, Model model) {
       
        //System.out.println("photoAlbumReadPage 진입");
       
        UserDto userDto = (UserDto) session.getAttribute("sessionUser");
        int useid = userDto.getUser_pk();
        int club_pk = clubService.selectClubPK(useid);

        List<Map<String, Object>> albumBoardList = clubService.selectAlbumFreeBoard(club_pk,clubimgboard_pk);
        model.addAttribute("albumBoardList", albumBoardList);

        //System.out.println("albumBoardList: "+albumBoardList);
        return "club/photoAlbumReadPage";
    }

    
}
