package com.psychopath.dogstalking.commons.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.KakaoUserDto;
import com.psychopath.dogstalking.dto.UserDto;

@Mapper
public interface UserSqlMapper {

    public void insertUser(UserDto userDto);

    public void insertKUser(UserDto userDto);

    public UserDto selectByUserIdAndPassword(UserDto userDto);

    public int selectUserPk();
    
    public void insertKakaoUser(KakaoUserDto kakaoUserDto);

    public Map<String, Object> selectKakaoUser(long kakao_user_id);

    public KakaoUserDto selectByKakaoUser(KakaoUserDto kakaoUserDto);

    public List<Map<String,Object>> selectMstarUserMainPage();
}
