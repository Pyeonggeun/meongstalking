package com.psychopath.dogstalking.commons.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.DogDto;

@Mapper
public interface DogSqlMapper {
    public void insertDog(DogDto dogDto);

}
