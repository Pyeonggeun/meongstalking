package com.psychopath.dogstalking.follow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;

@Mapper
public interface FollowSqlMapper {

    public List<Map<String, Object>> getMyTrackMarkers(int user_pk, int trackMarkerDateValidity);
    public List<Map<String, Object>> getTracingTrackMarkers(int user_pk, int trackMarkerDateValidity);

    public int isFirstTimeLeavingTrackMark(int user_pk);

    public void insertMoreInfo(UserMoreDto userMoreDto);

    public int getCurrentMyTrackMarkerCount(int user_pk);
    public int isEnoughDistanceFromMyTrackMarker(
        @RequestParam("user_pk") int user_pk,  @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude,
        @RequestParam("trackMarkerEffectiveDistance") int trackMarkerEffectiveDistance, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity);
    
    public void insertWriteTrackMarkInfo(LogDto logDto);

    public List<Map<String, Object>> getScanningTrackMarkers(@RequestParam("user_pk") int user_pk,
        @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity,
        @RequestParam("scanMarkerEffectiveDistance") int scanMarkerEffectiveDistance, @RequestParam("scanMarkerUpTo") int scanMarkerUpTo);

    public void insertCollectionInfo(CollectionDto collectionDto);

    public int getCurrentCollectTrackMarkerCount(int user_pk);
    // public List<Map<String, Object>> getCollectionPersonList(int user_pk);

    // public List<Map<String, Object>> getTrackLikeList(int user_pk);
    // public Map<String, Object> getClosestMarkLatLng(@RequestParam("user_pk") int user_pk, @RequestParam("user_writer_pk") int user_writer_pk,
    //     @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude);
    // public int isTracing(@RequestParam("user_pk") int user_pk, @RequestParam("log_pk") int log_pk);

    // public void insertTrackLikeLogInfo(LikeLogDto likeLogDto);

    // public int isLike(LikeDto likeDto);
    // public void insertLike(LikeDto likeDto);
    // public void deleteLike(LikeDto likeDto);

}
