package com.psychopath.dogstalking.mstar.controller;

import java.util.Map;

import javax.management.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ArticleLikeDto;
import com.psychopath.dogstalking.mstar.dto.CmtLikeDto;
import com.psychopath.dogstalking.mstar.dto.CommentDto;
import com.psychopath.dogstalking.mstar.dto.DirectDto;
import com.psychopath.dogstalking.mstar.dto.FollowDto;
import com.psychopath.dogstalking.mstar.dto.NotificationDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
import com.psychopath.dogstalking.mstar.dto.TcommentDto;
import com.psychopath.dogstalking.mstar.service.MstarServiceImpl;

import jakarta.servlet.http.HttpSession;

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
    public RestResponseDto loadMyProfileInfo(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

        Map<String, Object> map = mstarService.getProfileInfo(user_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;

    }
    @RequestMapping("loadMyProfileInfoForFollow")
    public RestResponseDto loadMyProfileInfoForFollow(int profile_info_pk, int another_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        Map<String, Object> map = mstarService.loadMyProfileInfoForFollowStatus(profile_info_pk, another_info_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;

    }
    @RequestMapping("loadAnotherProfileInfo")
    public RestResponseDto loadAnotherProfileInfo(int profile_info_pk, int another_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        Map<String, Object> map = mstarService.loadAnotherProfileInfo(profile_info_pk, another_info_pk);

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
    @RequestMapping("loadMyScrapArticleList")
    public RestResponseDto loadMyScrapArticleList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

       List<Map<String, Object>> list = mstarService.getMyScrapArticleList(profile_info_pk);
       
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
        
        mstarService.insertMyStory(imageFiles, storyDto);
    }

    @RequestMapping("insertStoryStorage")
    public void insertStoryStorage(MultipartFile imageFiles, StorageDto storageDto, int[] storyPkList){
        mstarService.insertMyStoryStorage(imageFiles, storageDto, storyPkList);

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
    @RequestMapping("loadScrapArticleList")
    public RestResponseDto loadScrapArticleList(int profile_info_pk, int user_pk){
        RestResponseDto responseDto = new RestResponseDto();

       Map<String, Object> map = mstarService.getUserScrapArticleList(profile_info_pk, user_pk);

        responseDto.setResult("success");
        responseDto.setData(map);

        return responseDto;
    }
    @RequestMapping("getUserDto")
    public RestResponseDto getUserDto(HttpSession session){
        RestResponseDto responseDto = new RestResponseDto();
        UserDto userDto  = (UserDto)session.getAttribute("sessionUser");

        responseDto.setResult("success");
        responseDto.setData(userDto);

        return responseDto;
    }
    
    @RequestMapping("anotherUserDto")
    public RestResponseDto anotherUserDto(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        UserDto userDto = mstarService.getUserDto(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(userDto);

        return responseDto;
    }

    @RequestMapping("followingAnotherUser")
    public void followingAnotherUser(FollowDto followDto){
        mstarService.insertFollow(followDto);
    }
    
    @RequestMapping("unFollowingAnotherUser")
    public void unFollowingAnotherUser(FollowDto followDto){
        mstarService.deleteFollow(followDto);
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

    @RequestMapping("loadStorageStoryList")
    public RestResponseDto loadStorageStoryList(int storage_pk){
        RestResponseDto responseDto = new RestResponseDto();

        List<Map<String, Object>> list = mstarService.getStorageStoryList(storage_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadStoryStorageInfo")
    public RestResponseDto loadStoryStorageInfo(int storage_pk){
        RestResponseDto responseDto = new RestResponseDto();

        StorageDto storageDto = mstarService.getStorageDto(storage_pk);
 
         responseDto.setResult("success");
         responseDto.setData(storageDto);
 
         return responseDto;

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
        
        List<Map<String, Object>> list = mstarService.selectSearchInfoList(profile_info_pk, search_text);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("insertDirect")
    public void insertDirect(DirectDto directDto){
        mstarService.insertDirectDto(directDto);
    }

    @RequestMapping("loadDirectChatList")
    public RestResponseDto loadDirectChatList(int profile_info_pk, int another_info_pk){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.selectDirectChatList(profile_info_pk, another_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadNewChat")
    public RestResponseDto loadNewChat(int direct_pk, int profile_info_pk, int another_info_pk){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.selectNewChatList(direct_pk, profile_info_pk, another_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadMyDirectReadStatus")
    public RestResponseDto loadMyDirectReadStatus(int direct_pk, int profile_info_pk, int another_info_pk){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        int loadMyDirectReadStatus = mstarService.selectChangeDirectStatus(direct_pk, profile_info_pk, another_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(loadMyDirectReadStatus);
 
         return responseDto;

    }
    @RequestMapping("loadUserFollowingList")
    public RestResponseDto loadUserFollowingList(int profile_info_pk, int another_info_pk, String searchText){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.userFollowingInfoList(profile_info_pk, another_info_pk,searchText);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadUserFollowList")
    public RestResponseDto loadUserFollowList(int profile_info_pk, int another_info_pk, String searchText){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.userFollowInfoList(profile_info_pk, another_info_pk,searchText);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadMyFollowList")
    public RestResponseDto loadMyFollowList(int profile_info_pk, String searchText){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.myFollowInfoList(profile_info_pk, searchText);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadMyFollowingList")
    public RestResponseDto loadMyFollowingList(int profile_info_pk, String searchText){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.myFollowingInfoList(profile_info_pk ,searchText);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadFollowFollowingList")
    public RestResponseDto loadFollowFollowingList(int profile_info_pk, int another_info_pk, String searchText){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<Map<String, Object>> list = mstarService.followFollowingInfoList(profile_info_pk, another_info_pk,searchText);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }

    @RequestMapping("blockAnotherUser")
    public void blockAnotherUser(int profile_info_pk, int another_info_pk){
        mstarService.userBlock(profile_info_pk, another_info_pk);
    }
    @RequestMapping("unBlockAnotherUser")
    public void unBlockAnotherUser(int profile_info_pk, int another_info_pk){
        mstarService.userUnBlock(profile_info_pk, another_info_pk);
    }

    @RequestMapping("articleScrap")
    public void articleScrap(int profile_info_pk, int article_pk){
        mstarService.insertArticleScrap(profile_info_pk, article_pk);
    }
    @RequestMapping("removeArticleScrap")
    public void removeArticleScrap(int profile_info_pk, int article_pk){
        mstarService.deleteArticleScrap(profile_info_pk, article_pk);
    }
    
    @RequestMapping("loadMyAllStoryList")
    public RestResponseDto loadMyAllStoryList(int profile_info_pk){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        List<StoryDto> list = mstarService.myStoryList(profile_info_pk);
 
         responseDto.setResult("success");
         responseDto.setData(list);
 
         return responseDto;

    }
    @RequestMapping("loadMyStoryViewInfo")
    public RestResponseDto loadMyStoryViewInfo(int story_pk){
        
        RestResponseDto responseDto = new RestResponseDto();
        
        Map<String,Object> map = mstarService.getMyStoryViewInfo(story_pk);
 
         responseDto.setResult("success");
         responseDto.setData(map);
 
         return responseDto;

    }
    

    @RequestMapping("insertBookMark")
    public void insertBookMark(int profile_info_pk, int another_info_pk){
        mstarService.insertBookMarkUser(profile_info_pk, another_info_pk);
    }
    @RequestMapping("deleteBookMark")
    public void deleteBookMark(int profile_info_pk, int another_info_pk){
        mstarService.deleteBookMarkUser(profile_info_pk, another_info_pk);
    }
    
    @RequestMapping("insertViewStory")
    public void insertViewStory(int story_pk, int profile_info_pk){
        mstarService.insertViewStoryDto(story_pk, profile_info_pk);
    }
    @RequestMapping("loadMyStoryList")
    public RestResponseDto loadMyStoryList(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();
        List<StoryDto> list = mstarService.getMyStoryList(profile_info_pk);

        responseDto.setResult("success");
        responseDto.setData(list);
 
         return responseDto;
    }

    @RequestMapping("updateDirectReadStatus")
    public void updateDirectReadStatus(int profile_info_pk, int another_info_pk){
        mstarService.changeReadStatus(profile_info_pk,another_info_pk);
    }
    
    @RequestMapping("loadUnReadMyNotificationList")
    public RestResponseDto loadUnReadMyNotificationList(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();
        List<NotificationDto> list = mstarService.getUnReadNotificationList(user_pk);

        responseDto.setResult("success");
        responseDto.setData(list);
 
         return responseDto;
    }
    @RequestMapping("loadReadMyNotificationList")
    public RestResponseDto loadReadMyNotificationList(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();
        List<NotificationDto> list = mstarService.getReadNotificationList(user_pk);

        responseDto.setResult("success");
        responseDto.setData(list);
 
         return responseDto;
    }
    @RequestMapping("checkUnReadNotify")
    public RestResponseDto checkUnReadNotify(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();
        int unReadStatus = mstarService.checkUnReadNotifyCount(user_pk);

        responseDto.setResult("success");
        responseDto.setData(unReadStatus);
 
         return responseDto;
    }

    @RequestMapping("checkUnReadDirect")
    public RestResponseDto checkUnReadDirect(int user_pk){
        RestResponseDto responseDto = new RestResponseDto();
        int unReadStatus = mstarService.checkUnReadDirectCount(user_pk);

        responseDto.setResult("success");
        responseDto.setData(unReadStatus);
 
         return responseDto;
    }
    @RequestMapping("updateNotifyReadStatus")
    public void updateNotifyReadStatus(int user_pk){
        mstarService.changeNotifyReadStatus(user_pk);
    }
    
    

    
}
