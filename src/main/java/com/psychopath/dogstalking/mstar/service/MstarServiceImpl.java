package com.psychopath.dogstalking.mstar.service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mstar.dto.ArtPhotoDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ArticleLikeDto;
import com.psychopath.dogstalking.mstar.dto.CmtLikeDto;
import com.psychopath.dogstalking.mstar.dto.CommentDto;
import com.psychopath.dogstalking.mstar.dto.DirectDto;
import com.psychopath.dogstalking.mstar.dto.FollowDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
import com.psychopath.dogstalking.mstar.dto.TcommentDto;
import com.psychopath.dogstalking.mstar.mapper.MstarSqlMapper;

@Service
public class MstarServiceImpl {
    @Autowired
    private MstarSqlMapper mstarSqlMapper;
    
    public ProfileInfoDto getMyProfileInfoDto(int user_pk){
        return mstarSqlMapper.selectProfileInfoDto(user_pk);
    }

    public Map<String, Object> getProfileInfo(int user_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
        int user_info_pk = profileInfoDto.getProfile_info_pk();

        int articleCount = mstarSqlMapper.selectArticleCount(user_info_pk);
        int followCount = mstarSqlMapper.selectFollowCount(user_info_pk);
        int followingCount = mstarSqlMapper.selectFollowingCount(user_info_pk);

        map.put("profileInfoDto", profileInfoDto);
        map.put("articleCount", articleCount);
        map.put("followCount", followCount);
        map.put("followingCount", followingCount);

        return map;
        
    }
    public Map<String, Object> loadAnotherProfileInfo(int profile_info_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        int user_info_pk = profileInfoDto.getProfile_info_pk();
        int user_pk = profileInfoDto.getUser_pk();
        UserDto userDto = mstarSqlMapper.selectUserDto(user_pk);
        int articleCount = mstarSqlMapper.selectArticleCount(user_info_pk);
        int followCount = mstarSqlMapper.selectFollowCount(user_info_pk);
        int followingCount = mstarSqlMapper.selectFollowingCount(user_info_pk);

        map.put("userDto", userDto);
        map.put("profileInfoDto", profileInfoDto);
        map.put("articleCount", articleCount);
        map.put("followCount", followCount);
        map.put("followingCount", followingCount);

        return map;
        
    }

    public void updateProfileInfo(MultipartFile imageFiles, ProfileInfoDto profileInfoDto){
        if(imageFiles != null) {
            
            String rootPath = "/Users/joinchun/uploadFiles/";
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String todayPath = sdf.format(new Date());
            
            File todayFolderForCreate = new File(rootPath + todayPath);
            
            if(!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }
            
            String originalFileName = imageFiles.getOriginalFilename();
            
           
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_"+ currentTime;
            
            
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            
            fileName += ext;
            
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            String macName = "/uploadFiles/";
            profileInfoDto.setProfile_photo(macName+todayPath+fileName);
            System.out.println("실행됨");
        }
        mstarSqlMapper.updateProfileInfoDto(profileInfoDto);
    }

    public void insertArticleInfoAndPhoto(List<MultipartFile> imageFiles, ArticleDto articleDto){
        
        mstarSqlMapper.insertArticle(articleDto);
        int article_pk = mstarSqlMapper.selectMaxArticlePk();

        
        
        if(imageFiles != null) {
			for(MultipartFile multipartFile : imageFiles) {
				if(multipartFile.isEmpty()) {
					continue;
				}
				
                ArtPhotoDto artPhotoDto = new ArtPhotoDto();
                artPhotoDto.setArticle_pk(article_pk);
				
                
				String rootPath = "/Users/joinchun/uploadFiles/";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM//dd/");
				String todayPath = sdf.format(new Date());
				
				File todayFolderForCreate = new File(rootPath + todayPath);
				
				if(!todayFolderForCreate.exists()) {
					todayFolderForCreate.mkdirs();
				}
				
				String originalFileName = multipartFile.getOriginalFilename();
				
				
				String uuid = UUID.randomUUID().toString();
				long currentTime = System.currentTimeMillis();
				String fileName = uuid + "_"+ currentTime;
				
				
				String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
				
				fileName += ext;
				
				try {
					multipartFile.transferTo(new File(rootPath+todayPath+fileName));
				}catch(Exception e) {
					e.printStackTrace();
				}
				String macName = "/uploadFiles/";
                artPhotoDto.setPhoto_path(macName+todayPath+fileName);
                mstarSqlMapper.insertArticlePhoto(artPhotoDto);
            }
        }
    }
    public List<Map<String, Object>> getMyArticleList(int profile_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();

        List<ArticleDto> articleList =  mstarSqlMapper.selectMyArticleDtoList(profile_info_pk);
        for(ArticleDto articleDto : articleList){
            Map<String, Object> map = new HashMap<>();

            int article_pk = articleDto.getArticle_pk();
            List<ArtPhotoDto> artPhotoList = mstarSqlMapper.selectArticlePhotoList(article_pk);

            map.put("articleDto", articleDto);
            map.put("artPhotoList", artPhotoList);
            list.add(map);
        }


        return list;
    }
    public void insertMyStory(MultipartFile imageFiles, StoryDto storyDto){
        if(imageFiles != null) {
            
            String rootPath = "/Users/joinchun/uploadFiles/";
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String todayPath = sdf.format(new Date());
            
            File todayFolderForCreate = new File(rootPath + todayPath);
            
            if(!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }
            
            String originalFileName = imageFiles.getOriginalFilename();
            
           
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_"+ currentTime;
            
            
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            
            fileName += ext;
            
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            String macName = "/uploadFiles/";
            storyDto.setStory_photo(macName+todayPath+fileName);
        }
        mstarSqlMapper.insertStoryDto(storyDto);
    }


    public void insertMyStoryStorage(MultipartFile imageFiles, StorageDto storageDto){
        if(imageFiles != null) {
            
            String rootPath = "/Users/joinchun/uploadFiles/";
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String todayPath = sdf.format(new Date());
            
            File todayFolderForCreate = new File(rootPath + todayPath);
            
            if(!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }
            
            String originalFileName = imageFiles.getOriginalFilename();
            
           
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_"+ currentTime;
            
            
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            
            fileName += ext;
            
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            String macName = "/uploadFiles/";
            storageDto.setStorage_img(macName+todayPath+fileName);
        }
        mstarSqlMapper.insertStoryStroageDto(storageDto);
    
    }

    public List<StorageDto> getStoryStorageList(int profile_info_pk){

        List<StorageDto> list = mstarSqlMapper.selectStoryStorageList(profile_info_pk);

        return list;
    }

    public List<Map<String, Object>> getArticleList(int profile_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();
        List<ArticleDto> artList = mstarSqlMapper.selectArticleList();

        for(ArticleDto articleDto : artList){
            Map<String , Object> map = new HashMap<>();
            int articleUserPk = articleDto.getProfile_info_pk();
            ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(articleUserPk);
            int user_pk = profileInfoDto.getUser_pk();
            UserDto userDto = mstarSqlMapper.selectUserDto(user_pk);
            int article_pk = articleDto.getArticle_pk();
            List<ArtPhotoDto> photoList = mstarSqlMapper.selectArticlePhotoList(article_pk);

            int followStatus = mstarSqlMapper.checkFollowStatus(articleUserPk, profile_info_pk);

            // 대댓글 갯수도 가져와야행...
            int articleCommentCount = mstarSqlMapper.selectArticleCommentCount(article_pk);
            int tCommentCount = mstarSqlMapper.selectTotalTcommentCount(article_pk);
            int totalCommentCount = articleCommentCount+tCommentCount;
            //좋아요 여부
            int articleLikeStatus = mstarSqlMapper.checkMyArticleLikeCount(article_pk, profile_info_pk);
            int likeCount = mstarSqlMapper.selectArticleLikeCount(article_pk);
            List<ProfileInfoDto> followerArticleLikeList =  mstarSqlMapper.selectProfileInfoArticleLikeAndFollow(article_pk, profile_info_pk);
            List<Map<String, Object>> likeList = new ArrayList<>();

            System.out.println(followerArticleLikeList.size());
            
            for(ProfileInfoDto likeProfileInfoDto : followerArticleLikeList){
                Map<String , Object> likeMap = new HashMap<>();

                int likeuser_pk = likeProfileInfoDto.getUser_pk();
                UserDto likeUserDto = mstarSqlMapper.selectUserDto(likeuser_pk);
                System.out.println(likeUserDto.getUserid());
                

                likeMap.put("profileInfoDto", likeProfileInfoDto);
                likeMap.put("userDto", likeUserDto);
                
                likeList.add(likeMap);
            }
            
            map.put("likeList", likeList);
            map.put("likeCount", likeCount);
            map.put("articleLikeStatus", articleLikeStatus);
            map.put("articleCommentCount", totalCommentCount);
            map.put("followStatus", followStatus);
            map.put("userDto", userDto);
            map.put("profileInfoDto", profileInfoDto);
            map.put("articleDto", articleDto);
            map.put("photoList", photoList);

            
            list.add(map);
        }
        return list;
    }
    public Map<String, Object> getProfileArticleList(int profile_info_pk, int my_user_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto myProfileInfoDto =  mstarSqlMapper.selectProfileInfoDto(my_user_pk);
        int myProfile_info_pk = myProfileInfoDto.getProfile_info_pk();

        ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        int user_pk = profileInfoDto.getUser_pk();
        UserDto userDto = mstarSqlMapper.selectUserDto(user_pk);
        
        int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, myProfile_info_pk);

        map.put("myProfileInfoDto", myProfileInfoDto);
        map.put("profileInfoDto", profileInfoDto);
        map.put("userDto", userDto);
        map.put("followStatus", followStatus);

        List<ArticleDto> artList = mstarSqlMapper.selectProfileArticleList(profile_info_pk);
        List<Map<String, Object>> list = new ArrayList<>();
        for(ArticleDto articleDto : artList){
            Map<String , Object> articleMap = new HashMap<>();
            
            int article_pk = articleDto.getArticle_pk();
            List<ArtPhotoDto> photoList = mstarSqlMapper.selectArticlePhotoList(article_pk);
            
            // 대댓글 갯수도 가져와야행...
            int articleCommentCount = mstarSqlMapper.selectArticleCommentCount(article_pk);
            int tCommentCount = mstarSqlMapper.selectTotalTcommentCount(article_pk);
            int totalCommentCount = articleCommentCount+tCommentCount;
            //좋아요 여부
            int articleLikeStatus = mstarSqlMapper.checkMyArticleLikeCount(article_pk, myProfile_info_pk);
            int likeCount = mstarSqlMapper.selectArticleLikeCount(article_pk);
            List<ProfileInfoDto> followerArticleLikeList =  mstarSqlMapper.selectProfileInfoArticleLikeAndFollow(article_pk, myProfile_info_pk);
            
            List<Map<String, Object>> likeList = new ArrayList<>();
            
            for(ProfileInfoDto likeProfileInfoDto : followerArticleLikeList){
                Map<String , Object> likeMap = new HashMap<>();

                int likeuser_pk = likeProfileInfoDto.getUser_pk();
                UserDto likeUserDto = mstarSqlMapper.selectUserDto(likeuser_pk);
                System.out.println(likeUserDto.getUserid());
                

                likeMap.put("profileInfoDto", likeProfileInfoDto);
                likeMap.put("userDto", likeUserDto);
                
                likeList.add(likeMap);
            }
            
            articleMap.put("likeList", likeList);
            articleMap.put("likeCount", likeCount);
            articleMap.put("articleLikeStatus", articleLikeStatus);
            articleMap.put("articleCommentCount", totalCommentCount);
            articleMap.put("articleDto", articleDto);
            articleMap.put("photoList", photoList);

            //좋아요 수,, 댓글 등등 더 들어와야함.
            list.add(articleMap);
        }
        map.put("list", list);
        
        return map;
    }
    public void insertFollow(int profile_info_pk, int user_pk){
        FollowDto followDto = new FollowDto();
        int following_user_pk =  mstarSqlMapper.selectProfileInfoDto(user_pk).getProfile_info_pk();
        followDto.setFollow_user_pk(profile_info_pk);
        followDto.setFollowing_user_pk(following_user_pk);

        mstarSqlMapper.insertUserFollowing(followDto);
    }
    
    public CommentDto insertComment(CommentDto commentDto){
        mstarSqlMapper.insertArticleComment(commentDto);
        int profile_info_pk = commentDto.getProfile_info_pk();
        CommentDto insertedCommentDto = mstarSqlMapper.selectInsertedMyComment(profile_info_pk);

        return insertedCommentDto;
    }
    public int insertTcomment(TcommentDto tcommentDto){
        mstarSqlMapper.insertArticleTcomment(tcommentDto);
        int comment_pk = tcommentDto.getComment_pk();
        int tCommentCount = mstarSqlMapper.selectTcommentCountBycommentPk(comment_pk);
    
        return tCommentCount;

    }

    public List<Map<String, Object>> getArticleCommentList(int article_pk){
        List<Map<String, Object>> list = new ArrayList<>();

        List<CommentDto> commentList = mstarSqlMapper.selectArticleCommentList(article_pk);
        
        for(CommentDto commentDto : commentList){
            Map<String , Object> map = new HashMap<>();

            int comment_pk = commentDto.getComment_pk();
            int profile_info_pk = commentDto.getProfile_info_pk();
            ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
            int user_pk =  profileInfoDto.getUser_pk();
            UserDto userDto =  mstarSqlMapper.selectUserDto(user_pk);

            int tCommentCount = mstarSqlMapper.selectTcommentCount(comment_pk);
            int commentLike = mstarSqlMapper.selectCommentLike(comment_pk);

            int checkWriterLikeComment = mstarSqlMapper.selectWriterLikeComment(comment_pk, profile_info_pk);           
            int writerProfilePk = mstarSqlMapper.selectWriterProfilePk(article_pk);
            int commentLikeCount = mstarSqlMapper.selectCommentLikeCount(comment_pk);

            map.put("checkWriterLikeComment", checkWriterLikeComment);
            map.put("writerProfilePk", writerProfilePk);
            map.put("commentLikeCount", commentLikeCount);
            map.put("commentDto", commentDto);
            map.put("profileInfoDto", profileInfoDto);
            map.put("userDto", userDto);
            map.put("tCommentCount", tCommentCount);
            map.put("commentLike", commentLike);

            list.add(map);
        }

        return list;
    }
    public Map<String,Object> insertCmtLikeDto(CmtLikeDto cmtLikeDto){
        Map<String , Object> map = new HashMap<>();
        mstarSqlMapper.insertCommentLike(cmtLikeDto);
        
        int comment_pk = cmtLikeDto.getComment_pk();
        // 좋아요 누른사람이 글 주인인지 확인해야함...
        int profile_info_pk = cmtLikeDto.getProfile_info_pk();
        int writerProfilePk = mstarSqlMapper.selectWriterProfilePkByCommnet(comment_pk);

        // 댓글 좋아요 카운트 다시가져와야함.
        int commentLikeCount = mstarSqlMapper.selectCommentLikeCount(comment_pk);

        map.put("profile_info_pk", profile_info_pk);
        map.put("writerProfilePk", writerProfilePk);
        map.put("commentLikeCount", commentLikeCount);
        
        return map;
    }
    public Map<String,Object> deleteCommentLike(CmtLikeDto cmtLikeDto){
        Map<String , Object> map = new HashMap<>();
        mstarSqlMapper.deleteCommentLike(cmtLikeDto);

        int comment_pk = cmtLikeDto.getComment_pk();
        
        int profile_info_pk = cmtLikeDto.getProfile_info_pk();
        int writerProfilePk = mstarSqlMapper.selectWriterProfilePkByCommnet(comment_pk);

        int commentLikeCount = mstarSqlMapper.selectCommentLikeCount(comment_pk);
        
        map.put("profile_info_pk", profile_info_pk);
        map.put("writerProfilePk", writerProfilePk);
        map.put("commentLikeCount", commentLikeCount);
        
        return map;
    }   
    public List<Map<String, Object>> getCommetTcomment(int comment_pk){
        List<Map<String, Object>> list = new ArrayList<>();
        List<TcommentDto> TcommentList = mstarSqlMapper.selectTcommentList(comment_pk);

        for(TcommentDto tCommentDto : TcommentList){
            Map<String , Object> map = new HashMap<>();
            
            ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(tCommentDto.getProfile_info_pk());
            UserDto userDto = mstarSqlMapper.selectUserDto(profileInfoDto.getUser_pk());

            map.put("tCommentDto", tCommentDto);
            map.put("profileInfoDto", profileInfoDto);
            map.put("userDto", userDto);
            
            list.add(map);
        }   
        return list;
    }
    public void insertArticleLike(ArticleLikeDto articleLikeDto){
        mstarSqlMapper.insertArticleLike(articleLikeDto);
    }
    public void deleteArticleLike(ArticleLikeDto articleLikeDto){
        mstarSqlMapper.deleteArticleLike(articleLikeDto);
    }
    
    public List<Map<String, Object>> getStoryList(int profile_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();
        int[] storyList =  mstarSqlMapper.selectStoryList(profile_info_pk);

        for(int profile_pk : storyList){
            Map<String, Object> map = new HashMap<>();
            
            ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(profile_pk);
            UserDto userDto =  mstarSqlMapper.selectUserDto(profileInfoDto.getUser_pk());

            map.put("profileInfoDto", profileInfoDto);
            map.put("userDto", userDto);

            list.add(map);
        }

        return list; 

    }

    public Map<String, Object> getUserStoryList(int profile_info_pk, int myProfile_info_pk){
        Map<String, Object> map = new HashMap<>();
        List<StoryDto> list = mstarSqlMapper.selectUserStoryList(profile_info_pk);
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        UserDto userDto = mstarSqlMapper.selectUserDto(profileInfoDto.getUser_pk());

        System.out.println("서비스 단 실행됨");

        List<Map<String, Object>> storyList = new ArrayList<>(); 
        for(StoryDto storyDto: list){
            Map<String, Object> storyMap = new HashMap<>();
            int story_pk = storyDto.getStory_pk();

            int viewStatus = mstarSqlMapper.selectViewStoryStatus(story_pk, myProfile_info_pk);
            System.out.println("서비스단 실행 2");
            storyMap.put("storyDto", storyDto);
            storyMap.put("viewStatus", viewStatus);

            storyList.add(storyMap);
        }

        map.put("storyList", storyList);
        map.put("profileInfoDto", profileInfoDto);
        map.put("userDto", userDto);

        return map;
    }

    public StoryDto getStoryInfo(int story_pk){
        return  mstarSqlMapper.selectStoryDto(story_pk);

    }

    //다이렉트!!!!!!
    public List<Map<String, Object>> selectDirectList(int profile_info_pk){
        List<Map<String, Object>> list = new ArrayList<>(); 

        int[] directingUserList = mstarSqlMapper.selectMyDirectList(profile_info_pk);
        for(int another_info_pk : directingUserList){
            Map<String, Object> map = new HashMap<>();
            ProfileInfoDto anotherProfileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk);
            int anotherUser_pk = anotherProfileInfoDto.getUser_pk();
            UserDto anotherUserDto = mstarSqlMapper.selectUserDto(anotherUser_pk);
            //마지막 다이렉트 가지고와야함.
            DirectDto directDto = mstarSqlMapper.selectLastDirectDto(profile_info_pk, another_info_pk);

            map.put("directDto", directDto);
            map.put("anotherProfileInfoDto", anotherProfileInfoDto);
            map.put("anotherUserDto", anotherUserDto);

            list.add(map);
        }

        return list;
    }
    // 리저트 맵 구조로 바꿔보자!! 퍼포먼스 업업!!
    public List<Map<String, Object>> selectSearchInfoList(int profile_info_pk, String search_text){
        
        List<Map<String, Object>> list = new ArrayList<>(); 

        List<UserDto> userList = mstarSqlMapper.selectSearchUserList(profile_info_pk, search_text);
        
        for(UserDto userDto : userList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = userDto.getUser_pk();
            ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int another_info_pk = profileInfoDto.getProfile_info_pk();
            int followingFollowingCount = mstarSqlMapper.selectMyFollowingUserFollowing(another_info_pk, profile_info_pk);
            
            map.put("followingFollowingCount", followingFollowingCount);
            map.put("userDto", userDto);
            map.put("profileInfoDto", profileInfoDto);

            list.add(map);
        }

        return list;        
    }

    public void insertDirectDto(DirectDto directDto){
        mstarSqlMapper.insertDirectChat(directDto);
    }
}
