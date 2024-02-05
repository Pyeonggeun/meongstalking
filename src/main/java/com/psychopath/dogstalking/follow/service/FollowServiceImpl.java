package com.psychopath.dogstalking.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;

@Service
public class FollowServiceImpl {

    @Autowired
    private FollowSqlMapper followSqlMapper;

    // public boolean isFirstTimeMark(int user_pk) {

    //     return followSqlMapper.isFirstTimeMark(user_pk) > 0 ? false : true;
    // }

    public void insertMoreInfo(UserMoreDto userMoreDto) {

        followSqlMapper.insertMoreInfo(userMoreDto);
    }

    // public List<LogDto> checkWriteMarkDistance(
    //     int user_pk, int markingRadius, int markingDate, double latitude, double longitude) {

    //     return followSqlMapper.checkWriteMarkDistance(user_pk, markingRadius, markingDate, latitude, longitude);
    // }

    // public int checkWriteMarkCount(int user_pk) {

    //     return followSqlMapper.checkWriteMarkCount(user_pk);
    // }

    public void insetWriteMarkInfo(LogDto logDto) {

        followSqlMapper.insetWriteMarkInfo(logDto);
    }

    // public LogDto getJustBeforeIMarked(int user_pk) {

    //     return followSqlMapper.getJustBeforeIMarked(user_pk);
    // }

    // public List<Map<String, Object>> getScanResult(int user_pk, double latitude, double longitude) {

    //     return followSqlMapper.getScanResult(user_pk, latitude, longitude);
    // }

    // public void insertCollectionInfo(CollectionDto collectionDto) {

    //     followSqlMapper.insertCollectionInfo(collectionDto);
    // }

    // public List<Map<String, Object>> getCollectionPersonList(int user_pk) {

    //     return followSqlMapper.getCollectionPersonList(user_pk);
    // }

    // public List<Map<String, Object>> getTrackLikeList(int user_pk) {

    //     return followSqlMapper.getTrackLikeList(user_pk);
    // }

    // public Map<String, Object> getClosestMarkLatLng(int user_pk, int user_writer_pk, double latitude, double longitude) {

    //     return followSqlMapper.getClosestMarkLatLng(user_pk, user_writer_pk, latitude, longitude);
    // }

    // public boolean isTracing(int user_pk, int log_pk) {

    //     return followSqlMapper.isTracing(user_pk, log_pk) <= 0 ? false : true;
    // }

    // public void insertTrackLikeLogInfo(LikeLogDto likeLogDto) {

    //     followSqlMapper.insertTrackLikeLogInfo(likeLogDto);
    // }

    // public List<Map<String, Object>> getTrackingList(int user_pk) {

    //     return followSqlMapper.getTrackingList(user_pk);
    // }

    // public void toggleLike(LikeDto likeDto) {
    //     if(followSqlMapper.isLike(likeDto) <= 0) {
    //         followSqlMapper.insertLike(likeDto);
    //     }else {
    //         followSqlMapper.deleteLike(likeDto);
    //     }
    // }

}
