package com.psychopath.dogstalking.follow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;

@Mapper
public interface FollowSqlMapper {

    public int isFirstTimeMark(int user_pk);

    public void insertMoreInfo(UserMoreDto userMoreDto);

    public List<LogDto> checkWriteMarkDistance(
        @RequestParam("user_pk") int user_pk, @RequestParam("markingRadius") int markingRadius,
        @RequestParam("markingDate") int markingDate, @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude);
    public int checkWriteMarkCount(int user_pk);

    public void insetWriteMarkInfo(LogDto logDto);
    public LogDto getJustBeforeIMarked(int user_pk);

    public List<Map<String, Object>> getScanResult(@RequestParam("user_pk") int user_pk,
        @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude);

}
