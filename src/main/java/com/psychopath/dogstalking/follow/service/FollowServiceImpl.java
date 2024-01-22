package com.psychopath.dogstalking.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.follow.dto.LogDto;
import com.psychopath.dogstalking.follow.dto.UserMoreDto;
import com.psychopath.dogstalking.follow.mapper.FollowSqlMapper;

@Service
public class FollowServiceImpl {

    @Autowired
    private FollowSqlMapper followSqlMapper;

    public boolean isFirstTimeMark(int user_pk) {

        return followSqlMapper.isFirstTimeMark(user_pk) > 0 ? false : true;
    }

    public void insertMoreInfo(UserMoreDto userMoreDto) {

        followSqlMapper.insertMoreInfo(userMoreDto);
    }

    public List<LogDto> checkWriteMarkDistance(
        int user_pk, int markingRadius, int markingDate, double latitude, double longitude) {

        return followSqlMapper.checkWriteMarkDistance(user_pk, markingRadius, markingDate, latitude, longitude);
    }

    public int checkWriteMarkCount(int user_pk) {

        return followSqlMapper.checkWriteMarkCount(user_pk);
    }

    public void insetWriteMarkInfo(LogDto logDto) {

        followSqlMapper.insetWriteMarkInfo(logDto);
    }

    public LogDto getJustBeforeIMarked(int user_pk) {

        return followSqlMapper.getJustBeforeIMarked(user_pk);
    }

    public List<Map<String, Object>> getScanResult(int user_pk, double latitude, double longitude) {

        return followSqlMapper.getScanResult(user_pk, latitude, longitude);
    }

}
