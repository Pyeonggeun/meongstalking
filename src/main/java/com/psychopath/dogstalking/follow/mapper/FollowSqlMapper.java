package com.psychopath.dogstalking.follow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;

@Mapper
public interface FollowSqlMapper {

    public int isFirstTimeMark(int user_pk);

    public void insertMoreInfo(UserMoreDto userMoreDto);

    public List<LogDto> checkWriteMarkDistance(
        int user_pk, int markingRadius, int markingDate, double latitude, double longitude);
    public int checkWriteMarkCount(int user_pk);

    public void insetWriteMarkInfo(LogDto logDto);

    public List<Map<String, Object>> getScanResult(int user_pk, double latitude, double longitude);

}
