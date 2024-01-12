package com.psychopath.dogstalking.mstar.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;



@Mapper
public interface MstarSqlMapper{

    public void insertCommonInfo(ProfileInfoDto profileInfoDto);

    public ProfileInfoDto selectMyProfileInfoDto(int user_pk);


}
