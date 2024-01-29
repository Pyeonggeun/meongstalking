package com.psychopath.dogstalking.commons.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.commons.mapper.UserSqlMapper;
import com.psychopath.dogstalking.dto.KakaoUserDto;
import com.psychopath.dogstalking.dto.UserDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;
import com.psychopath.dogstalking.mstar.mapper.MstarSqlMapper;

@Service
public class UserServiceImpl {

    @Autowired
    private UserSqlMapper userSqlMapper;
    @Autowired
    private MstarSqlMapper mstarSqlMapper;

    public void register_user(UserDto userDto){
        userSqlMapper.insertUser(userDto);

        ProfileInfoDto profileInfoDto = new ProfileInfoDto();
        int num = (int)(Math.random()*4)+1 ;
        String profile_photo = "/public/image/commons/commonsDogImg"+num+".png";

        int user_pk = userSqlMapper.selectUserPk();

        profileInfoDto.setUser_pk(user_pk);
        profileInfoDto.setProfile_photo(profile_photo);

        mstarSqlMapper.insertCommonInfo(profileInfoDto);

        int profile_info_pk = mstarSqlMapper.selectMaxProfilePk();
        String nick_name = "멍스토킹@S"+profile_info_pk+"01lec";
        mstarSqlMapper.updateFirstNickName(profile_info_pk, nick_name);
        
    }

    public UserDto getUserInfoByUserIdAndPassword(UserDto userDto){
		
		UserDto result = userSqlMapper.selectByUserIdAndPassword(userDto);
		return result; 
	}

    public ProfileInfoDto getProfilePhotoPath(int user_pk){
        return mstarSqlMapper.selectProfileInfoDto(user_pk);
    }

    public void saveKakaoUser(KakaoUserDto kakaoUserDto) {
        userSqlMapper.insertKakaoUser(kakaoUserDto);
    }

    public Map<String, Object> getKakaoUser(long kakao_user_id) {
        return userSqlMapper.selectKakaoUser(kakao_user_id);
    }

    public void insertKUser(UserDto userDto){
        userSqlMapper.insertKUser(userDto);
    }

    public void selectByKakaoUser(KakaoUserDto kakaoUserDto){
        userSqlMapper.selectByKakaoUser(kakaoUserDto);
    }
}
