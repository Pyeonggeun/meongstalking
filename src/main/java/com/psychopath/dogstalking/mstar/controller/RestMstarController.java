package com.psychopath.dogstalking.mstar.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.mstar.dto.ArticleDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.dto.StorageDto;
import com.psychopath.dogstalking.mstar.dto.StoryDto;
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
        mstarService.insertMyStory(imageFiles, storyDto);
    }

    @RequestMapping("insertStoryStorage")
    public void insertStoryStorage(MultipartFile imageFiles, StorageDto storageDto){
        mstarService.insertMyStoryStorage(imageFiles, storageDto);
    }

    

    
}
