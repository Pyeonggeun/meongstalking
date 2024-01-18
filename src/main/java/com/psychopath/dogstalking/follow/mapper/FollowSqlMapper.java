package com.psychopath.dogstalking.follow.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.follow.dto.UserMoreDto;

@Mapper
public interface FollowSqlMapper {

    public int isFirstTimeMark(int user_pk);

    public void insertMoreInfo(UserMoreDto userMoreDto);

}
