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

import com.psychopath.dogstalking.mstar.dto.ArtPhotoDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
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
            System.out.println(rootPath+todayPath+fileName);
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            profileInfoDto.setProfile_photo(todayPath+fileName);
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
				
                artPhotoDto.setPhoto_path(todayPath+fileName);
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
            System.out.println(rootPath+todayPath+fileName);
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            storyDto.setStory_photo(todayPath+fileName);
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
            System.out.println(rootPath+todayPath+fileName);
            try {
                imageFiles.transferTo(new File(rootPath+todayPath+fileName));
            }catch(Exception e) {
                e.printStackTrace();
            }
            storageDto.setStorage_img(todayPath+fileName);
        }
        mstarSqlMapper.insertStoryStroageDto(storageDto);
    
    }

    public List<StorageDto> getStoryStorageList(int profile_info_pk){

        List<StorageDto> list = mstarSqlMapper.selectStoryStorageList(profile_info_pk);

        return list;
    }
    
}
