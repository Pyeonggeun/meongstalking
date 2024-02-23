package com.psychopath.dogstalking.follow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.follow.dto.CollectionDto;
import com.psychopath.dogstalking.follow.dto.CommentDto;
import com.psychopath.dogstalking.follow.dto.LikeDto;
import com.psychopath.dogstalking.follow.dto.LikeLogDto;
import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.mFollow.dto.UserItemDto;
import com.psychopath.dogstalking.mstar.dto.NotificationDto;

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
    public void insertComment(CommentDto commentDto);

    public int getCurrentCollectTrackMarkerCount(int user_pk);

    public List<Map<String, Object>> getLikeList(@RequestParam("user_pk") int user_pk, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity);

    public Map<String, Object> getClosestTrackMarker(@RequestParam("user_pk") int user_pk, @RequestParam("user_writer_pk") int user_writer_pk,
        @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity);
        
    public void insertLikeLogInfo(LikeLogDto likeLogDto);

    public int isLike(LikeDto likeDto);
    public void insertLike(LikeDto likeDto);
    public void deleteLike(LikeDto likeDto);

    public List<Map<String, Object>> getCollectionList(int user_pk);
    public List<Map<String, Object>> getCollectionLogList(@RequestParam("user_pk") int user_pk,
        @RequestParam("user_writer_pk") int user_writer_pk, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity);
    public List<Map<String, Object>> getCommentList(int log_pk);

    public List<Map<String, Object>> getPermanentItemLv(int user_pk);
    public int getPlusTrackMarker(@RequestParam("user_pk") int user_pk, @RequestParam("user_writer_pk") int user_writer_pk);

    public List<Map<String, Object>> getItemCount(int user_pk);
    public void insertUserItemInfo(UserItemDto userItemDto);

    public void sendMessage(NotificationDto notificationDto);

    public Map<String, Object> getMyPageInfo(int user_pk);
    public List<Map<String, Object>> getMyTrackMarkersInfo(@RequestParam("user_pk") int user_pk, @RequestParam("trackMarkerDateValidity") int trackMarkerDateValidity);
    public int getTracingTrackMarkerLogPk(@RequestParam("user_pk") int user_pk, @RequestParam("user_writer_pk") int user_writer_pk);

    public Integer getRandomLogPkExceptMine(int user_pk);

    public String isAlert(NotificationDto notificationDto);

    public void deleteLog();
    public void deleteComment();
    public void deleteCollection();
    public void deleteLikeLog();
    public void deleteLikeUserColletZero();

}
