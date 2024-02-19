package com.psychopath.dogstalking.follow.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.CommentDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UseItemDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.service.FollowServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/follow")
public class RestFollowController {

    @Autowired
    private FollowServiceImpl followService;

    @RequestMapping("getMyUserPk")
    public RestResponseDto getMyUserPk(HttpSession session) {

        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUser");
        
        restResponseDto.setData(userDto);
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }
    
    @RequestMapping("getMyTrackMarkers")
    public RestResponseDto getMyTrackMarkers(int user_pk, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getMyTrackMarkers(user_pk, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getTracingTrackMarkers")
    public RestResponseDto getTracingTrackMarkers(int user_pk, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getTracingTrackMarkers(user_pk, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isFirstTimeLeavingTrackMark")
    public RestResponseDto isFirstTimeLeavingTrackMark(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.isFirstTimeLeavingTrackMark(user_pk));
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

    @RequestMapping("getCurrentMyTrackMarkerCount")
    public RestResponseDto getCurrentMyTrackMarkerCount(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getCurrentMyTrackMarkerCount(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isEnoughDistanceFromMyTrackMarker")
    public RestResponseDto isEnoughDistanceFromMyTrackMarker(
        int user_pk, double latitude, double longitude, int trackMarkerEffectiveDistance, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(
            followService.isEnoughDistanceFromMyTrackMarker(user_pk, latitude, longitude, trackMarkerEffectiveDistance, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }
    
    @RequestMapping("insertWriteTrackMarkInfo")
    public RestResponseDto insertWriteTrackMarkInfo(LogDto params, MultipartFile imageFile) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        if(imageFile != null) {
            String rootPath = "C:/uploadFiles/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
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
        
        followService.insertWriteTrackMarkInfo(params);

        restResponseDto.setResult("success");

        return restResponseDto;
    }

    @RequestMapping("getScanningTrackMarkers")
    public RestResponseDto getScanningTrackMarkers(int user_pk, double latitude, double longitude,
    int trackMarkerDateValidity, int scanMarkerEffectiveDistance, int scanMarkerUpTo) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getScanningTrackMarkers(user_pk, latitude, longitude, trackMarkerDateValidity, scanMarkerEffectiveDistance, scanMarkerUpTo));
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

    @RequestMapping("getCurrentCollectTrackMarkerCount")
    public RestResponseDto getCurrentCollectTrackMarkerCount(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getCurrentCollectTrackMarkerCount(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getLikeList")
    public RestResponseDto getLikeList(int user_pk, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getLikeList(user_pk, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getClosestTrackMarker")
    public RestResponseDto getClosestTrackMarker(int user_pk, int user_writer_pk, double latitude, double longitude, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getClosestTrackMarker(user_pk, user_writer_pk, latitude, longitude, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("insertLikeLogInfo")
    public RestResponseDto insertLikeLogInfo(LikeLogDto params, double markLat, double markLng) {

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
        
        followService.insertLikeLogInfo(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("isLike")
    public RestResponseDto isLike(LikeDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.isLike(params));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("insertLike")
    public RestResponseDto insertLike(LikeDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertLike(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("deleteLike")
    public RestResponseDto deleteLike(LikeDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.deleteLike(params);

        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("insertComment")
    public RestResponseDto insertComment(CommentDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertComment(params);
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getCollectionList")
    public RestResponseDto getCollectionList(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getCollectionList(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getCollectionLogList")
    public RestResponseDto getCollectionLogList(int user_pk, int user_writer_pk, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getCollectionLogList(user_pk, user_writer_pk, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getCommentList")
    public RestResponseDto getCommentList(int log_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getCommentList(log_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getPlusTrackMarker")
    public RestResponseDto getPlusTrackMarker(int user_pk, int user_writer_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getPlusTrackMarker(user_pk, user_writer_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getPermanentItemLv")
    public RestResponseDto getPermanentItemLv(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getPermanentItemLv(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getItemCount")
    public RestResponseDto getItemCount(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setData(followService.getItemCount(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("insertUserItemInfo")
    public RestResponseDto insertUserItemInfo(UseItemDto params) {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.insertUserItemInfo(params);
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("sendMessage")
    public RestResponseDto sendMessage() {

        RestResponseDto restResponseDto = new RestResponseDto();

        followService.sendMessage();
        
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getMyPageInfo")
    public RestResponseDto getMyPageInfo(int user_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getMyPageInfo(user_pk));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }
    
    @RequestMapping("getMyTrackMarkersInfo")
    public RestResponseDto getMyTrackMarkersInfo(int user_pk, int trackMarkerDateValidity) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getMyTrackMarkersInfo(user_pk, trackMarkerDateValidity));
        restResponseDto.setResult("success");
        
        return restResponseDto;
    }

    @RequestMapping("getTracingTrackMarkerLogPk")
    public RestResponseDto getTracingTrackMarkerLogPk(int user_pk, int user_writer_pk) {

        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.setData(followService.getTracingTrackMarkerLogPk(user_pk, user_writer_pk));
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
