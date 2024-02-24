package com.psychopath.dogstalking.mstar.service;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.io.Reader;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.comprehend.model.DetectSentimentRequest;
import software.amazon.awssdk.services.comprehend.model.DetectSentimentResponse;
import software.amazon.awssdk.services.comprehend.model.LanguageCode;

import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mstar.dto.ArtPhotoDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ArticleLikeDto;
import com.psychopath.dogstalking.mstar.dto.ArticleScrapDto;
import com.psychopath.dogstalking.mstar.dto.ArticleTagDto;
import com.psychopath.dogstalking.mstar.dto.BlockDto;
import com.psychopath.dogstalking.mstar.dto.BookmarkDto;
import com.psychopath.dogstalking.mstar.dto.CmtLikeDto;
import com.psychopath.dogstalking.mstar.dto.CommentDto;
import com.psychopath.dogstalking.mstar.dto.DirectDto;
import com.psychopath.dogstalking.mstar.dto.FollowDto;
import com.psychopath.dogstalking.mstar.dto.NotificationDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
import com.psychopath.dogstalking.mstar.dto.StorySaveDto;
import com.psychopath.dogstalking.mstar.dto.TcommentDto;
import com.psychopath.dogstalking.mstar.dto.ViewStoryDto;
import com.psychopath.dogstalking.mstar.mapper.MstarSqlMapper;




@Service
public class MstarServiceImpl {
    @Autowired
    private MstarSqlMapper mstarSqlMapper;
    

    private String getPropertyValue(String key){

        String value = "";

        try {
            Reader reader = Resources.getResourceAsReader("properties/keys.properties");
            Properties properties = new Properties();
            properties.load(reader);

            value = properties.getProperty(key);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    private String sentimentAwsComprehend(String content){

       String property_accessKey  =getPropertyValue("aws-comprehends-access-key");
        String property_secretKey  =getPropertyValue("aws-comprehends-secret-key");
        
        // AWS 계정의 액세스 키와 시크릿 키를 설정

        String accessKey = property_accessKey;

        String secretKey = property_secretKey;


        // AWS 계정의 액세스 키와 시크릿 키를 사용하여 Comprehend 클라이언트 생성

        ComprehendClient comprehendClient = ComprehendClient.builder()

        .region(Region.AP_NORTHEAST_2) // 원하는 리전으로 변경

        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))

        .build();


        // 분석할 텍스트

        String textToAnalyze = content;


        // 감정 분석 예제

        //detectSentiment(comprehendClient, textToAnalyze);
        DetectSentimentRequest detectSentimentRequest = DetectSentimentRequest.builder()

                .languageCode(LanguageCode.KO)

                .text(textToAnalyze)

                .build();


        DetectSentimentResponse sentimentResponse = comprehendClient.detectSentiment(detectSentimentRequest);


        String emotion = sentimentResponse.sentiment().toString();

        
        return emotion;

    }

    public ProfileInfoDto getMyProfileInfoDto(int user_pk){
        return mstarSqlMapper.selectProfileInfoDto(user_pk);
    }

    public Map<String, Object> getProfileInfo(int user_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
        int user_info_pk = profileInfoDto.getProfile_info_pk();
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
    public Map<String, Object> loadMyProfileInfoForFollowStatus(int profile_info_pk, int another_info_pk){
        Map<String, Object> map = new HashMap<>();
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        UserDto userDto = mstarSqlMapper.selectUserDtoByPIP(profile_info_pk);
        int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, another_info_pk);
        int followingStatus = mstarSqlMapper.checkFollowingStatus(another_info_pk, profile_info_pk);

        map.put("profileInfoDto", profileInfoDto);
        map.put("userDto", userDto);
        map.put("followStatus", followStatus);
        map.put("followingStatus", followingStatus);

        return map;
    }

    public Map<String, Object> loadAnotherProfileInfo(int profile_info_pk, int another_info_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk);
        
        int another_user_pk = profileInfoDto.getUser_pk();
        UserDto userDto = mstarSqlMapper.selectUserDto(another_user_pk);
        int articleCount = mstarSqlMapper.selectArticleCount(another_info_pk);
        int followCount = mstarSqlMapper.selectFollowCount(another_info_pk);
        int followingCount = mstarSqlMapper.selectFollowingCount(another_info_pk);

        int user_pk = mstarSqlMapper.selectUserDtoByPIP(profile_info_pk).getUser_pk();
        int blockStatus = mstarSqlMapper.selectBlockStatus(user_pk, another_user_pk);

        int bookmarkStatus =  mstarSqlMapper.selectBookMarkStatus(profile_info_pk, another_info_pk);



        int[] followingFollowingPkList = mstarSqlMapper.selectMyFollowingUserFollowing(another_info_pk, profile_info_pk);
        if(followingFollowingPkList.length != 0){
            List<Map<String, Object>> followingFollowList = new ArrayList<>();
            for(int e : followingFollowingPkList){
                Map<String, Object> followingFollowMap = new HashMap<>();
                UserDto followingFollowUser = mstarSqlMapper.selectUserDtoByPIP(e);
                ProfileInfoDto followingFollowProfile = mstarSqlMapper.selectProfileInfoDtoByPIP(e);
                followingFollowMap.put("followingFollowUser", followingFollowUser);
                followingFollowMap.put("followingFollowProfile", followingFollowProfile);

                followingFollowList.add(followingFollowMap);
            }
            
            map.put("followingFollowList", followingFollowList);
        }
        
        int followingFollowingCount = followingFollowingPkList.length;
        int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, another_info_pk);

        map.put("blockStatus", blockStatus);
        map.put("bookmarkStatus", bookmarkStatus);
        map.put("followStatus", followStatus);
        map.put("followingFollowingCount", followingFollowingCount);
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
            System.out.println(profileInfoDto.getProfile_photo());
        }
        System.out.println("실행됨");
        System.out.println(profileInfoDto);
        mstarSqlMapper.updateProfileInfoDto(profileInfoDto);

    }

    public int insertArticleInfo(ArticleDto articleDto){
        String content = articleDto.getContent();
        // String emotion =  sentimentAwsComprehend(content);
        // articleDto.setEmotion(emotion);
        mstarSqlMapper.insertArticle(articleDto);
        int article_pk = mstarSqlMapper.selectMaxArticlePk();
        return article_pk;
    }
    
    @Transactional
    public void insertArticlePhotoAndTag(MultipartFile imageFiles,int article_pk,int view_order, int[] tag_info_pk, double[] x_coordinates, double[] y_coordinates){

        if(imageFiles != null) {				
            ArtPhotoDto artPhotoDto = new ArtPhotoDto();
            artPhotoDto.setArticle_pk(article_pk);
            artPhotoDto.setView_order(view_order);
            
            String rootPath = "/Users/joinchun/uploadFiles/";
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM//dd/");
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
            artPhotoDto.setPhoto_path(macName+todayPath+fileName);
            mstarSqlMapper.insertArticlePhoto(artPhotoDto);
            if(tag_info_pk != null){
                for(int x = 0; x < tag_info_pk.length; x++ ){
                    int art_photo_pk = mstarSqlMapper.selectMaxArticlePhotoPk();
                    System.out.println("서비스단"+ art_photo_pk);
                    ArticleTagDto articleTagDto = new ArticleTagDto();
                    articleTagDto.setArt_photo_pk(art_photo_pk);
                    articleTagDto.setTag_info_pk(tag_info_pk[x]);
                    articleTagDto.setX_coordinates(x_coordinates[x]);
                    articleTagDto.setY_coordinates(y_coordinates[x]);
                    System.out.println("서비스단 실행됨"+ art_photo_pk);
                    mstarSqlMapper.insertArticleTag(articleTagDto);
                }
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
    public List<Map<String, Object>> getMyScrapArticleList(int profile_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();

        List<ArticleDto> articleList =  mstarSqlMapper.selectMyScrapArticleDtoList(profile_info_pk);
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


    public void insertMyStoryStorage(MultipartFile imageFiles, StorageDto storageDto, int[] storyPkList){
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
        int storage_pk =  mstarSqlMapper.selectStoragePk();
        for(int e : storyPkList){
            StorySaveDto storySaveDto = new StorySaveDto();
            storySaveDto.setStorage_pk(storage_pk);
            storySaveDto.setStory_pk(e);

            mstarSqlMapper.insertSaveStory(storySaveDto);
        }
    
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
            int another_info_pk = articleDto.getProfile_info_pk();
            ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk);
            int user_pk = profileInfoDto.getUser_pk();
            UserDto userDto = mstarSqlMapper.selectUserDto(user_pk);
            int article_pk = articleDto.getArticle_pk();
            List<ArtPhotoDto> photoList = mstarSqlMapper.selectArticlePhotoList(article_pk);
            List<Map<String, Object>> photoAndTagList = new ArrayList<>();
            for(ArtPhotoDto artPhotoDto : photoList){
                Map<String , Object> photoAndTagMap = new HashMap<>();
                int art_photo_pk = artPhotoDto.getArt_photo_pk();
                List<ArticleTagDto> tagList = mstarSqlMapper.selectArticleTagList(art_photo_pk);
                List<Map<String, Object>> userTagList = new ArrayList<>();
                if(tagList.size() != 0){
                    for(ArticleTagDto articleTagDto : tagList){
                        Map<String , Object> userTagMap = new HashMap<>();
                        int tag_info_pk = articleTagDto.getTag_info_pk();
                        String userid = mstarSqlMapper.selectUserDtoByPIP(tag_info_pk).getUserid();
                        userTagMap.put("userid", userid);
                        userTagMap.put("articleTagDto", articleTagDto);
                        userTagList.add(userTagMap);
                    }
                }
                photoAndTagMap.put("artPhotoDto", artPhotoDto);
                photoAndTagMap.put("userTagList", userTagList);
                photoAndTagList.add(photoAndTagMap);
            }

            int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, another_info_pk);
            int scrapStatus = mstarSqlMapper.selectScrapStatus(article_pk, profile_info_pk);
            // 대댓글 갯수도 가져와야행...
            int articleCommentCount = mstarSqlMapper.selectArticleCommentCount(article_pk);
            int tCommentCount = mstarSqlMapper.selectTotalTcommentCount(article_pk);
            int totalCommentCount = articleCommentCount+tCommentCount;
            //좋아요 여부
            int articleLikeStatus = mstarSqlMapper.checkMyArticleLikeCount(article_pk, profile_info_pk);
            int likeCount = mstarSqlMapper.selectArticleLikeCount(article_pk);
            List<ProfileInfoDto> followerArticleLikeList =  mstarSqlMapper.selectProfileInfoArticleLikeAndFollow(article_pk, profile_info_pk);
            List<Map<String, Object>> likeList = new ArrayList<>();
            


            for(ProfileInfoDto likeProfileInfoDto : followerArticleLikeList){
                Map<String , Object> likeMap = new HashMap<>();

                int likeuser_pk = likeProfileInfoDto.getUser_pk();
                UserDto likeUserDto = mstarSqlMapper.selectUserDto(likeuser_pk);

                likeMap.put("profileInfoDto", likeProfileInfoDto);
                likeMap.put("userDto", likeUserDto);
                
                likeList.add(likeMap);
            }
            
            map.put("scrapStatus", scrapStatus);
            map.put("likeList", likeList);
            map.put("likeCount", likeCount);
            map.put("articleLikeStatus", articleLikeStatus);
            map.put("articleCommentCount", totalCommentCount);
            map.put("followStatus", followStatus);
            map.put("userDto", userDto);
            map.put("profileInfoDto", profileInfoDto);
            map.put("articleDto", articleDto);
            map.put("photoAndTagList", photoAndTagList);

            
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
        
        int followStatus = mstarSqlMapper.checkFollowStatus(myProfile_info_pk, profile_info_pk);

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
            int scrapStatus = mstarSqlMapper.selectScrapStatus(article_pk, myProfile_info_pk);
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

                likeMap.put("profileInfoDto", likeProfileInfoDto);
                likeMap.put("userDto", likeUserDto);
                
                likeList.add(likeMap);
            }
            
            articleMap.put("scrapStatus", scrapStatus);
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
    
    public Map<String, Object> getUserScrapArticleList(int profile_info_pk, int my_user_pk){
        Map<String, Object> map = new HashMap<>();
        
        ProfileInfoDto myProfileInfoDto =  mstarSqlMapper.selectProfileInfoDto(my_user_pk);
        int myProfile_info_pk = myProfileInfoDto.getProfile_info_pk();

        // ProfileInfoDto profileInfoDto =  mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        // map.put("profileInfoDto", profileInfoDto);
        // int user_pk = profileInfoDto.getUser_pk();
        UserDto scrapUserDto = mstarSqlMapper.selectUserDtoByPIP(profile_info_pk);
        map.put("scrapUserDto", scrapUserDto);
        map.put("myProfileInfoDto", myProfileInfoDto);
        

        List<ArticleDto> artList = mstarSqlMapper.selectMyScrapArticleDtoList(profile_info_pk);
        
        List<Map<String, Object>> list = new ArrayList<>();
        for(ArticleDto articleDto : artList){
            Map<String , Object> articleMap = new HashMap<>();
            
            int article_pk = articleDto.getArticle_pk();

            int ArticleProfile_info_pk = articleDto.getProfile_info_pk();

            int followStatus = mstarSqlMapper.checkFollowStatus(myProfile_info_pk,ArticleProfile_info_pk);

            int scrapStatus = mstarSqlMapper.selectScrapStatus(article_pk, myProfile_info_pk);
            ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(ArticleProfile_info_pk);
            UserDto userDto = mstarSqlMapper.selectUserDtoByPIP(ArticleProfile_info_pk);
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

                likeMap.put("profileInfoDto", likeProfileInfoDto);
                likeMap.put("userDto", likeUserDto);
                
                likeList.add(likeMap);
            }
            
            articleMap.put("scrapStatus", scrapStatus);
            articleMap.put("followStatus", followStatus);
            articleMap.put("likeList", likeList);
            articleMap.put("profileInfoDto", profileInfoDto);
            articleMap.put("userDto", userDto);
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


    public void insertFollow(FollowDto followDto){
        mstarSqlMapper.insertUserFollowing(followDto);
    }
    public void deleteFollow(FollowDto followDto){
        mstarSqlMapper.deleteUserFollowing(followDto);
        int profile_info_pk = followDto.getFollowing_user_pk(); 
        int another_info_pk = followDto.getFollow_user_pk();
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBkm_user_pk(profile_info_pk);
        bookmarkDto.setRcv_user_pk(another_info_pk);
        System.out.println(bookmarkDto);
        mstarSqlMapper.deleteUserBookMark(bookmarkDto);
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
    public UserDto getUserDto(int profile_info_pk){
        return mstarSqlMapper.selectUserDtoByPIP(profile_info_pk);
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

    public void insertArticleScrap(int profile_info_pk, int article_pk){
        ArticleScrapDto articleScrapDto = new ArticleScrapDto();
        articleScrapDto.setArticle_pk(article_pk);
        articleScrapDto.setProfile_info_pk(profile_info_pk);
        mstarSqlMapper.insertArticleScrap(articleScrapDto);
    }
    public void deleteArticleScrap(int profile_info_pk, int article_pk){
        ArticleScrapDto articleScrapDto = new ArticleScrapDto();
        articleScrapDto.setArticle_pk(article_pk);
        articleScrapDto.setProfile_info_pk(profile_info_pk);
        mstarSqlMapper.deleteArticleScrap(articleScrapDto);
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
    public List<Map<String, Object>> getStorageStoryList(int storage_pk){
        List<Map<String, Object>> list = new ArrayList<>();

        List<StoryDto> storyList =  mstarSqlMapper.selectStorageStoryList(storage_pk);

        for(StoryDto storyDto : storyList){
            Map<String, Object> map = new HashMap<>();
            
            map.put("storyDto", storyDto);

            list.add(map);
        }
        return list; 

    }
    public StorageDto getStorageDto(int storage_pk){
        return mstarSqlMapper.selectStorageDto(storage_pk);
    }

    public Map<String, Object> getUserStoryList(int profile_info_pk, int myProfile_info_pk){
        Map<String, Object> map = new HashMap<>();
        List<StoryDto> list = mstarSqlMapper.selectUserStoryList(profile_info_pk);
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        UserDto userDto = mstarSqlMapper.selectUserDto(profileInfoDto.getUser_pk());

        List<Map<String, Object>> storyList = new ArrayList<>(); 
        for(StoryDto storyDto: list){
            Map<String, Object> storyMap = new HashMap<>();
            int story_pk = storyDto.getStory_pk();

            int viewStatus = mstarSqlMapper.selectViewStoryStatus(story_pk, myProfile_info_pk);

            storyMap.put("storyDto", storyDto);
            storyMap.put("viewStatus", viewStatus);

            storyList.add(storyMap);
        }

        map.put("storyList", storyList);
        map.put("profileInfoDto", profileInfoDto);
        map.put("userDto", userDto);

        return map;
    }
    public List<StoryDto> getMyStoryList(int profile_info_pk){
        List<StoryDto> list = mstarSqlMapper.selectMyStoryList(profile_info_pk);
        
        return list;
    }

    public StoryDto getStoryInfo(int story_pk){
        return  mstarSqlMapper.selectStoryDto(story_pk);

    }
    public Map<String, Object> getMyStoryViewInfo(int story_pk){
        Map<String,Object> map = new HashMap<>();

        int viewCount =  mstarSqlMapper.selectStoryViewCount(story_pk);
        List<ProfileInfoDto> list = mstarSqlMapper.selectStoryViewProfileInfo(story_pk);
        List<Map<String,Object>> viewList = new ArrayList<>();
        for(ProfileInfoDto profileInfoDto : list){
            Map<String,Object> viewMap = new HashMap<>();
            int profile_info_pk = profileInfoDto.getProfile_info_pk();
            UserDto userDto = mstarSqlMapper.selectUserDto(profile_info_pk);
            
            viewMap.put("profileInfoDto", profileInfoDto);
            viewMap.put("userDto", userDto);

            viewList.add(viewMap);
        }
        map.put("viewCount", viewCount);
        map.put("viewList", viewList);

        return map;
    }
    public int getArticleLikeCount(int article_pk){
       return mstarSqlMapper.selectArticleLikeCount(article_pk);
    }

    //다이렉트 리스트!!!!!!
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
    //다이렉트 채팅리스트
    public List<Map<String, Object>> selectDirectChatList(int profile_info_pk, int another_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();

        List<DirectDto> directList = mstarSqlMapper.selectDirectChatList(another_info_pk, profile_info_pk);

        if(directList.size() != 0){
            for(DirectDto directDto : directList){
                Map<String, Object> map = new HashMap<>();
                ProfileInfoDto anotherProfileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk);
                int anotherUser_pk = anotherProfileInfoDto.getUser_pk();
                UserDto anotherUserDto = mstarSqlMapper.selectUserDto(anotherUser_pk);
               
                map.put("directDto", directDto);
                map.put("directDto", directDto);
                map.put("anotherProfileInfoDto", anotherProfileInfoDto);
                map.put("anotherUserDto", anotherUserDto);
    
                list.add(map);
            }
        }

        return list;
    }
    public List<Map<String, Object>> selectNewChatList(int direct_pk, int profile_info_pk, int another_info_pk){
        List<Map<String, Object>> list = new ArrayList<>();
        
        List<DirectDto> directList = mstarSqlMapper.selectNewChatList(direct_pk, another_info_pk, profile_info_pk);
        if(directList.size() != 0){
            for(DirectDto directDto : directList){
                Map<String, Object> map = new HashMap<>();
                ProfileInfoDto anotherProfileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk);
                int anotherUser_pk = anotherProfileInfoDto.getUser_pk();
                UserDto anotherUserDto = mstarSqlMapper.selectUserDto(anotherUser_pk);
                
                map.put("directDto", directDto);
                map.put("anotherProfileInfoDto", anotherProfileInfoDto);
                map.put("anotherUserDto", anotherUserDto);
    
                list.add(map);
            }
        }
        
        return list;
    }
    public int selectChangeDirectStatus(int direct_pk, int profile_info_pk, int another_info_pk){
        
        
        return mstarSqlMapper.selectChangeDirectStatus(direct_pk, another_info_pk, profile_info_pk);
        
        
    }

    // 리저트 맵 구조로 바꿔보자!! 퍼포먼스 업업!!
    public List<Map<String, Object>> selectSearchInfoList(int profile_info_pk, String search_text){
        List<Map<String, Object>> list = new ArrayList<>(); 
        System.out.println(profile_info_pk);
        System.out.println(search_text);
        List<UserDto> userList = mstarSqlMapper.selectSearchUserList(profile_info_pk, search_text);
        System.out.println(userList);
        
        for(UserDto userDto : userList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = userDto.getUser_pk();
            ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int another_info_pk = profileInfoDto.getProfile_info_pk();

            int[] followingFollowingPkList = mstarSqlMapper.selectMyFollowingUserFollowing(another_info_pk, profile_info_pk);
            
            UserDto followUserDto = null;
            int followingFollowingCount = 0;
            if(followingFollowingPkList.length != 0){
                followUserDto = mstarSqlMapper.selectUserDtoByPIP(followingFollowingPkList[0]);
                followingFollowingCount = followingFollowingPkList.length;
            }
           


            map.put("followUserDto", followUserDto);
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

    public List<Map<String, Object>> userFollowInfoList(int profile_info_pk, int follow_user_pk, String searchText){
        
        List<Map<String, Object>> list = new ArrayList<>(); 

        List<UserDto> userList = mstarSqlMapper.selectFollowUserList(profile_info_pk, follow_user_pk, searchText);
        
        for(UserDto followUserDto : userList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = followUserDto.getUser_pk();
            ProfileInfoDto followProfileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int following_user_pk = followProfileInfoDto.getProfile_info_pk();
            int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, following_user_pk);

            map.put("followProfileInfoDto", followProfileInfoDto);
            map.put("followUserDto", followUserDto);
            map.put("followStatus", followStatus);
            

            list.add(map);
        }

        return list;        
    }
    public List<Map<String, Object>> myFollowInfoList(int profile_info_pk, String searchText){
        
        List<Map<String, Object>> list = new ArrayList<>(); 

        List<UserDto> userList = mstarSqlMapper.selectMyFollowList(profile_info_pk, searchText);
        
        for(UserDto followUserDto : userList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = followUserDto.getUser_pk();
            ProfileInfoDto followProfileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int following_user_pk = followProfileInfoDto.getProfile_info_pk();
            int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, following_user_pk);

            map.put("followProfileInfoDto", followProfileInfoDto);
            map.put("followUserDto", followUserDto);
            map.put("followStatus", followStatus);
            

            list.add(map);
        }

        return list;        
    }
    public List<Map<String, Object>> userFollowingInfoList(int profile_info_pk, int following_user_pk, String searchText){
        
        List<Map<String, Object>> list = new ArrayList<>(); 
        
        List<UserDto> UserList = mstarSqlMapper.selectFollowingUserList(profile_info_pk, following_user_pk, searchText);
        
        for(UserDto followUserDto : UserList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = followUserDto.getUser_pk();
            ProfileInfoDto followProfileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int follow_user_pk = followProfileInfoDto.getProfile_info_pk();
            int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, follow_user_pk);

            map.put("followProfileInfoDto", followProfileInfoDto);
            map.put("followUserDto", followUserDto);
            map.put("followStatus", followStatus);
            

            list.add(map);
        }

        return list;        
    }
    public List<Map<String, Object>> myFollowingInfoList(int profile_info_pk, String searchText){
        
        List<Map<String, Object>> list = new ArrayList<>(); 
        
        List<UserDto> UserList = mstarSqlMapper.selectMyFollowingList(profile_info_pk, searchText);
        
        for(UserDto followUserDto : UserList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = followUserDto.getUser_pk();
            ProfileInfoDto followProfileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);
            int follow_user_pk = followProfileInfoDto.getProfile_info_pk();
            int followStatus = mstarSqlMapper.checkFollowStatus(profile_info_pk, follow_user_pk);

            map.put("followProfileInfoDto", followProfileInfoDto);
            map.put("followUserDto", followUserDto);
            map.put("followStatus", followStatus);
            

            list.add(map);
        }

        return list;        
    }
    public List<Map<String, Object>> followFollowingInfoList(int profile_info_pk, int another_info_pk, String searchText){
        
        List<Map<String, Object>> list = new ArrayList<>(); 
        
        List<UserDto> UserList = mstarSqlMapper.selectFollowFollowingUserList(profile_info_pk, another_info_pk, searchText);
        
        for(UserDto followUserDto : UserList){
            Map<String, Object> map = new HashMap<>();
            int user_pk = followUserDto.getUser_pk();
            ProfileInfoDto followProfileInfoDto = mstarSqlMapper.selectProfileInfoDto(user_pk);

            map.put("followProfileInfoDto", followProfileInfoDto);
            map.put("followUserDto", followUserDto);

            list.add(map);
        }

        return list;        
    }

    public void userBlock(int profile_info_pk, int another_info_pk){
        BlockDto blockDto = new BlockDto();
       
        int user_pk = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk).getUser_pk();
        int block_user_pk = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk).getUser_pk();

        blockDto.setUser_pk(user_pk);
        blockDto.setBlock_user_pk(block_user_pk);

        mstarSqlMapper.insertBlockDto(blockDto);

        FollowDto followDto = new FollowDto();

        followDto.setFollow_user_pk(another_info_pk);
        followDto.setFollowing_user_pk(profile_info_pk);

        mstarSqlMapper.deleteUserFollowing(followDto);

        BookmarkDto bookmarkDto = new BookmarkDto();
    
        bookmarkDto.setBkm_user_pk(profile_info_pk);
        bookmarkDto.setRcv_user_pk(another_info_pk);

        mstarSqlMapper.deleteUserBookMark(bookmarkDto);
    }
    public void userUnBlock(int profile_info_pk, int another_info_pk){
        BlockDto blockDto = new BlockDto();
       
        int user_pk = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk).getUser_pk();
        int block_user_pk = mstarSqlMapper.selectProfileInfoDtoByPIP(another_info_pk).getUser_pk();

        blockDto.setUser_pk(user_pk);
        blockDto.setBlock_user_pk(block_user_pk);

        mstarSqlMapper.deleteUserBlock(blockDto);

    }
    public List<StoryDto> myStoryList(int profile_info_pk){

        return mstarSqlMapper.selectMyAllStoryList(profile_info_pk);
    }
    public void insertBookMarkUser(int profile_info_pk, int another_info_pk){
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBkm_user_pk(profile_info_pk);
        bookmarkDto.setRcv_user_pk(another_info_pk);
        mstarSqlMapper.insertBookMarkDto(bookmarkDto);
    }
    public void deleteBookMarkUser(int profile_info_pk, int another_info_pk){
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBkm_user_pk(profile_info_pk);
        bookmarkDto.setRcv_user_pk(another_info_pk);
        mstarSqlMapper.deleteUserBookMark(bookmarkDto);
    }
        
    public void insertViewStoryDto(int story_pk, int profile_info_pk){
        ViewStoryDto viewStoryDto = new ViewStoryDto();

        

        viewStoryDto.setProfile_info_pk(profile_info_pk);
        viewStoryDto.setStory_pk(story_pk);
        int viewStatus = mstarSqlMapper.checkStoryViewStatus(viewStoryDto);
        
        if(viewStatus == 0){
            mstarSqlMapper.insertViewStoryDto(viewStoryDto);
        }
    }

    public void changeReadStatus(int profile_info_pk, int another_info_pk){
        mstarSqlMapper.updateReadStatus(profile_info_pk, another_info_pk);
    }

    public List<NotificationDto> getUnReadNotificationList(int user_pk){
        return mstarSqlMapper.selectUnReadNotificationList(user_pk);
    }
    public List<NotificationDto> getReadNotificationList(int user_pk){
        return mstarSqlMapper.selectReadNotificationList(user_pk);
    }
    public void changeNotifyReadStatus(int user_pk){
        mstarSqlMapper.updateNotificationStatus(user_pk);
    }
    public int checkUnReadNotifyCount(int user_pk){
       return mstarSqlMapper.selectUnReadNotify(user_pk);
    }
    public int checkUnReadDirectCount(int user_pk){
        return mstarSqlMapper.selectUnReadDirect(user_pk);
    }

    public Map<String,Object> getUserProfileInfo(int profile_info_pk){
        Map<String, Object> map = new HashMap<>();
        ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(profile_info_pk);
        UserDto userDto = mstarSqlMapper.selectUserDtoByPIP(profile_info_pk);
        map.put("profileInfoDto", profileInfoDto);
        map.put("userDto", userDto);
        return map;
    }
    public int getMyStoryCount(int profile_info_pk){
        return mstarSqlMapper.selectStoryCount(profile_info_pk);
    }

    public List<ProfileInfoDto> getRecoProfileList(int user_pk){
        
        List<ProfileInfoDto> list = new ArrayList<>();
        int[] profile_pk_list = mstarSqlMapper.selectRecoUserList(user_pk);
        for(int i : profile_pk_list){
            ProfileInfoDto profileInfoDto = mstarSqlMapper.selectProfileInfoDtoByPIP(i);
            list.add(profileInfoDto);
        }

        return list;
    }

}
