package com.psychopath.dogstalking.follow.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.service.FollowServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/follow")
public class RestFollowController {

    @Autowired
    private FollowServiceImpl followService;

    @RequestMapping("getUserPk")
    public RestResponseDto getUserPk(HttpSession session) {

        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUser");
        
        restResponseDto.setData(userDto.getUser_pk());
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isFirstTimeMark")
    public RestResponseDto isFirstTimeMark(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.isFirstTimeMark(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }
    
    @RequestMapping("insertMoreInfo")
    public RestResponseDto insertMoreInfo(UserMoreDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertMoreInfo(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("checkWriteMarkDistance")
    public RestResponseDto checkWriteMarkDistance(
        int user_pk, int markingRadius, int markingDate, double latitude, double longitude) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(
            followService.checkWriteMarkDistance(user_pk, markingRadius, markingDate, latitude, longitude));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("checkWriteMarkCount")
    public RestResponseDto checkWriteMarkCount(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.checkWriteMarkCount(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("saveWriteMarkInfo")
    public RestResponseDto saveWriteMarkInfo(LogDto params, MultipartFile imageFile) {

        RestResponseDto restResponseDto = new RestResponseDto();
        System.out.println(imageFile);
        if(imageFile != null) {
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String todayPath = sdf.format(new Date());

            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()) {
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
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            params.setImage_link(todayPath + fileName);
        }
        
        followService.insetWriteMarkInfo(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getJustBeforeIMarked")
    public RestResponseDto getJustBeforeIMarked(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getJustBeforeIMarked(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getScanResult")
    public RestResponseDto getScanResult(int user_pk, double latitude, double longitude) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getScanResult(user_pk, latitude, longitude));
        restResponseDto.setResult("success");
        return restResponseDto;
    }

    @RequestMapping("insertCollectionInfo")
    public RestResponseDto insertCollectionInfo(CollectionDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertCollectionInfo(params);
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getCollectionPersonList")
    public RestResponseDto getCollectionPersonList(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getCollectionPersonList(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getTrackLikeList")
    public RestResponseDto getTrackLikeList(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getTrackLikeList(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getClosestMarkLatLng")
    public RestResponseDto getClosestMarkLatLng(int user_pk, double latitude, double longitude) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getClosestMarkLatLng(user_pk, latitude, longitude));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("insertTrackLikeLogInfo")
    public RestResponseDto insertTrackLikeLogInfo(LikeLogDto params, double markLat, double markLng) {

        RestResponseDto restResponseDto = new RestResponseDto();

        double d2r = Math.PI / 180;
        double r2d = 180 / Math.PI;
        double earth_rad = 6378000;

        double r = new Random().nextInt(1000) + new Random().nextDouble();
        double rlat = (r / earth_rad) * r2d;
        double rlng = rlat / Math.cos(markLat * d2r);

        double theta = Math.PI * (new Random().nextInt(2) + new Random().nextDouble());
        double y = markLng + (rlng * Math.cos(theta));
        double x = markLat + (rlat * Math.sin(theta));

        params.setLatitude(x);
        params.setLongitude(y);
        
        followService.insertTrackLikeLogInfo(params);

        restResponseDto.setData(params);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getTrackingList")
    public RestResponseDto getTrackingList(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getTrackingList(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isTracing")
    public RestResponseDto isTracing(int user_pk, int log_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.isTracing(user_pk, log_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("toggleLike")
    public RestResponseDto toggleLike(LikeDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.toggleLike(params);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("a")
    public RestResponseDto a() {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

}
