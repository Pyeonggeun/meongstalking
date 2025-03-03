package com.psychopath.dogstalking.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.CommentDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;
import com.psychopath.dogstalking.mFollow.dto.UserItemDto;
import com.psychopath.dogstalking.mstar.dto.NotificationDto;

@Service
public class FollowServiceImpl {

    @Autowired
    private FollowSqlMapper followSqlMapper;

    public List<Map<String, Object>> getMyTrackMarkers(int user_pk, int trackMarkerDateValidity) {

        return followSqlMapper.getMyTrackMarkers(user_pk, trackMarkerDateValidity);
    }

    public List<Map<String, Object>> getTracingTrackMarkers(int user_pk, int trackMarkerDateValidity) {

        return followSqlMapper.getTracingTrackMarkers(user_pk, trackMarkerDateValidity);
    }

    public boolean isFirstTimeLeavingTrackMark(int user_pk) {

        return followSqlMapper.isFirstTimeLeavingTrackMark(user_pk) <= 0 ? true : false;
    }

    public void insertMoreInfo(UserMoreDto userMoreDto) {

        followSqlMapper.insertMoreInfo(userMoreDto);
    }

    public int getCurrentMyTrackMarkerCount(int user_pk) {

        return followSqlMapper.getCurrentMyTrackMarkerCount(user_pk);
    }

    public boolean isEnoughDistanceFromMyTrackMarker(
        int user_pk, double latitude, double longitude, int trackMarkerEffectiveDistance, int trackMarkerDateValidity) {

        return followSqlMapper.isEnoughDistanceFromMyTrackMarker(user_pk, latitude, longitude, trackMarkerEffectiveDistance, trackMarkerDateValidity) <= 0 ? true : false;
    }

    public void insertWriteTrackMarkInfo(LogDto logDto) {

        followSqlMapper.insertWriteTrackMarkInfo(logDto);
    }

    public List<Map<String, Object>> getScanningTrackMarkers(int user_pk, double latitude, double longitude,
        int trackMarkerDateValidity, int scanMarkerEffectiveDistance, int scanMarkerUpTo) {

        return followSqlMapper.getScanningTrackMarkers(user_pk, latitude, longitude, trackMarkerDateValidity, scanMarkerEffectiveDistance, scanMarkerUpTo);
    }

    public void insertCollectionInfo(CollectionDto collectionDto) {

        followSqlMapper.insertCollectionInfo(collectionDto);
    }

    public void insertComment(CommentDto commentDto) {

        followSqlMapper.insertComment(commentDto);
    }

    public int getCurrentCollectTrackMarkerCount(int user_pk) {

        return followSqlMapper.getCurrentCollectTrackMarkerCount(user_pk);
    }

    public List<Map<String, Object>> getLikeList(int user_pk, int trackMarkerDateValidity) {

        return followSqlMapper.getLikeList(user_pk, trackMarkerDateValidity);
    }

    public Map<String, Object> getClosestTrackMarker(int user_pk, int user_writer_pk, double latitude, double longitude, int trackMarkerDateValidity) {

        return followSqlMapper.getClosestTrackMarker(user_pk, user_writer_pk, latitude, longitude, trackMarkerDateValidity);
    }

    public void insertLikeLogInfo(LikeLogDto likeLogDto) {

        followSqlMapper.insertLikeLogInfo(likeLogDto);
    }

    public boolean isLike(LikeDto likeDto) {

        return followSqlMapper.isLike(likeDto) > 0 ? true : false;
    }

    public void insertLike(LikeDto likeDto) {

        followSqlMapper.insertLike(likeDto);
    }

    public void deleteLike(LikeDto likeDto) {

        followSqlMapper.deleteLike(likeDto);
    }

    public List<Map<String, Object>> getCollectionList(int user_pk) {

        return followSqlMapper.getCollectionList(user_pk);
    }
    
    public List<Map<String, Object>> getCollectionLogList(int user_pk, int user_writer_pk, int trackMarkerDateValidity) {

        return followSqlMapper.getCollectionLogList(user_pk, user_writer_pk, trackMarkerDateValidity);
    }

    public List<Map<String, Object>> getCommentList(int log_pk) {

        return followSqlMapper.getCommentList(log_pk);
    }

    public int getPlusTrackMarker(int user_pk, int user_writer_pk) {

        return followSqlMapper.getPlusTrackMarker(user_pk, user_writer_pk);
    }

    public List<Map<String, Object>> getPermanentItemLv(int user_pk) {

        return followSqlMapper.getPermanentItemLv(user_pk);
    }

    public List<Map<String, Object>> getItemCount(int user_pk) {

        return followSqlMapper.getItemCount(user_pk);
    }
    
    public void insertUserItemInfo(UserItemDto userItemDto) {

        followSqlMapper.insertUserItemInfo(userItemDto);
    }

    public void sendMessage(NotificationDto notificationDto) {

        followSqlMapper.sendMessage(notificationDto);
    }

    public Map<String, Object> getMyPageInfo(int user_pk) {

        return followSqlMapper.getMyPageInfo(user_pk);
    }

    public List<Map<String, Object>> getMyTrackMarkersInfo(int user_pk, int trackMarkerDateValidity) {

        return followSqlMapper.getMyTrackMarkersInfo(user_pk, trackMarkerDateValidity);
    }

    public int getTracingTrackMarkerLogPk(int user_pk, int user_writer_pk) {

        return followSqlMapper.getTracingTrackMarkerLogPk(user_pk, user_writer_pk);
    }

    public String isAlert(NotificationDto notificationDto) {

        return followSqlMapper.isAlert(notificationDto);
    }

}
