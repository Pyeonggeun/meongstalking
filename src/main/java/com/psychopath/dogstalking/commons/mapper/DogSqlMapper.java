package com.psychopath.dogstalking.commons.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.psychopath.dogstalking.dto.DogDto;

@Mapper
public interface DogSqlMapper {
    public void insertDog(DogDto dogDto);

    public List<DogDto> getDogList(int userPk);

}
