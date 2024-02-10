package com.psychopath.dogstalking.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    public int getCurrentCollectTrackMarkerCount(int user_pk) {

        return followSqlMapper.getCurrentCollectTrackMarkerCount(user_pk);
    }

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

    // public void toggleLike(LikeDto likeDto) {
    //     if(followSqlMapper.isLike(likeDto) <= 0) {
    //         followSqlMapper.insertLike(likeDto);
    //     }else {
    //         followSqlMapper.deleteLike(likeDto);
    //     }
    // }

}
