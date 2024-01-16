package com.psychopath.dogstalking.commons.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.UserDto;

@Mapper
public interface UserSqlMapper {

    public void insertUser(UserDto userDto);
    public UserDto selectByUserIdAndPassword(UserDto userDto);

    public int selectUserPk();  
}
