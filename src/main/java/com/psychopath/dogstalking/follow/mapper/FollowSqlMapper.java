package com.psychopath.dogstalking.follow.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowSqlMapper {

    public int isFirstTimeMark(int user_pk);

}
