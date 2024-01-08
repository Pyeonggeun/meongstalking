package com.psychopath.dogstalking.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.commons.mapper.UserSqlMapper;
import com.psychopath.dogstalking.dto.UserDto;

@Service
public class UserServiceImpl {

    @Autowired
    private UserSqlMapper userSqlMapper;

    public void register_user(UserDto userDto){
        
        userSqlMapper.insertUser(userDto);
    }

    public UserDto getUserInfoByUserIdAndPassword(UserDto userDto){
		
		UserDto result = userSqlMapper.selectByUserIdAndPassword(userDto);
		return result; 
	}


}
