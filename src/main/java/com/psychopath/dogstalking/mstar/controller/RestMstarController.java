package com.psychopath.dogstalking.mstar.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ArticleLikeDto;
import com.psychopath.dogstalking.mstar.dto.CmtLikeDto;
import com.psychopath.dogstalking.mstar.dto.CommentDto;
import com.psychopath.dogstalking.mstar.dto.DirectDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
import com.psychopath.dogstalking.mstar.dto.TcommentDto;
import com.psychopath.dogstalking.mstar.service.MstarServiceImpl;

@RestController
@RequestMapping("/mstar/*")
public class RestMstarController {
    @Autowired
    private MstarServiceImpl mstarService;


    @RequestMapping("myProfileInfoDto")
    public RestResponseDto myProfileInfoDto(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        ProfileInfoDto profileInfoDto = mstarService.getMyProfileInfoDto(user_pk);
        responseDto.setResult("success");
        responseDto.setData(profileInfoDto);

        return responseDto;
    }

    @RequestMapping("loadMyProfileInfo")
    public RestResponseDto getMyProfileInfo(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        Map<String, Object> map = mstarService.getProfileInfo(user_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;

    }
    @RequestMapping("loadAnotherProfileInfo")
    public RestResponseDto loadAnotherProfileInfo(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        Map<String, Object> map = mstarService.loadAnotherProfileInfo(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;

    }
    
    @RequestMapping("updateProfileProcess")
    public void updateProfileProcess(MultipartFile imageFiles, ProfileInfoDto profileInfoDto){
        
        mstarService.updateProfileInfo(imageFiles, profileInfoDto);
		
    }
    @RequestMapping("insertArticleInfo")
    public void insertArticleInfo(List<MultipartFile> imageFiles, ArticleDto articleDto){
        mstarService.insertArticleInfoAndPhoto(imageFiles, articleDto);
    }
    @RequestMapping("loadMyArticeleList")
    public RestResponseDto loadMyArticeleList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

       List<Map<String, Object>> list = mstarService.getMyArticleList(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }
    @RequestMapping("loadStoryStorageList")
    public RestResponseDto loadStoryStorageList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

       List<StorageDto> list = mstarService.getStoryStorageList(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }
    @RequestMapping("insertStory")
    public void insertStory(MultipartFile imageFiles, StoryDto storyDto){
        System.out.println("무슨색~?");
        System.out.println(storyDto.getBackground_color());
        mstarService.insertMyStory(imageFiles, storyDto);
    }

    @RequestMapping("insertStoryStorage")
    public void insertStoryStorage(MultipartFile imageFiles, StorageDto storageDto){
        mstarService.insertMyStoryStorage(imageFiles, storageDto);
    }
    @RequestMapping("loadArticeleList")
    public RestResponseDto loadArticeleList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

       List<Map<String, Object>> list = mstarService.getArticleList(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }
    @RequestMapping("loadProfileArticleList")
    public RestResponseDto loadProfileArticleList(int profile_info_pk, int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

       Map<String, Object> map = mstarService.getProfileArticleList(profile_info_pk, user_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;
    }
    @RequestMapping("followingAnotherUser")
    public void followingAnotherUser(int profile_info_pk, int user_pk){
        mstarService.insertFollow(profile_info_pk, user_pk);
    }
    @RequestMapping("insertMyComment")
    public RestResponseDto insertMyComment(CommentDto commentDto){
        RestResponseDto responseDto = new RestResponseDto();
        CommentDto insertedCommendDto = mstarService.insertComment(commentDto);

        responseDto.setResult("success");
        responseDto.setData(insertedCommendDto);

        return responseDto;
    }
    @RequestMapping("insertMyTcomment")
    public void insertMyTcomment(TcommentDto tcommentDto){
        mstarService.insertTcomment(tcommentDto);
    }
    @RequestMapping("loadArticeleCommentList")
    public RestResponseDto loadArticeleCommentList(int article_pk){
        RestResponseDto responseDto = new RestResponseDto();

       List<Map<String, Object>> list = mstarService.getArticleCommentList(article_pk);

        responseDto.setResult("success");
        responseDto.setData(list);

        return responseDto;
    }
    @RequestMapping("likeComment")
    public RestResponseDto likeComment(CmtLikeDto cmtLikeDto){
        RestResponseDto responseDto = new RestResponseDto();
        Map<String, Object> map = mstarService.insertCmtLikeDto(cmtLikeDto);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;
    }
    @RequestMapping("deleteComment")
    public RestResponseDto deleteComment(CmtLikeDto cmtLikeDto){

        RestResponseDto responseDto = new RestResponseDto();
        Map<String, Object> map = mstarService.deleteCommentLike(cmtLikeDto);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;
    }
    @RequestMapping("loadTcommentList")
    public RestResponseDto loadTcommentList(int comment_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String, Object>> list = mstarService.getCommetTcomment(comment_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }

    @RequestMapping("insertMyArticleLike")
    public void insertMyArticleLike(ArticleLikeDto articleLikeDto){
    
        mstarService.insertArticleLike(articleLikeDto);
    }
    @RequestMapping("deleteMyArticleLike")
    public void deleteMyArticleLike(ArticleLikeDto articleLikeDto){
        mstarService.deleteArticleLike(articleLikeDto);
    }
    
    @RequestMapping("loadStoryList")
    public RestResponseDto loadStoryList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String, Object>> list = mstarService.getStoryList(profile_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }

    @RequestMapping("userStoryList")
    public RestResponseDto userStoryList(int profile_info_pk, int myProfile_info_pk){
        System.out.println("여기다");
        System.out.println(profile_info_pk);
        System.out.println(myProfile_info_pk);
        RestResponseDto responseDto = new RestResponseDto();

        Map<String,Object> map = mstarService.getUserStoryList(profile_info_pk, myProfile_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(map);
 
         return responseDto;

    }
    @RequestMapping("loadUserStory")
    public RestResponseDto loadUserStory(int story_pk){
        RestResponseDto responseDto = new RestResponseDto();

        StoryDto storyDto = mstarService.getStoryInfo(story_pk);
 
         responseDto.setResult("success");
         responseDto.setData(storyDto);
 
         return responseDto;

    }
    @RequestMapping("loadDirectUserList")
    public RestResponseDto loadDirectUserList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String, Object>> list = mstarService.selectDirectList(profile_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }

    @RequestMapping("searchUserInfo")
    public RestResponseDto searchUserInfo(int profile_info_pk, String search_text){
        
        RestResponseDto responseDto = new RestResponseDto();
        System.out.println(search_text);
        List<Map<String, Object>> list = mstarService.selectSearchInfoList(profile_info_pk,search_text);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("insertDirect")
    public void insertDirect(DirectDto directDto){
        mstarService.insertDirectDto(directDto);
    }



    
    
    
    
    

    
}
