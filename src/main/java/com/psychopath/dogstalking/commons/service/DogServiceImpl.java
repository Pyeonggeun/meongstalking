package com.psychopath.dogstalking.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psychopath.dogstalking.commons.mapper.DogSqlMapper;
import com.psychopath.dogstalking.dto.DogDto;

@Service
public class DogServiceImpl {

    @Autowired
    private DogSqlMapper dogMapper;

    public void registerDog(DogDto dogDto){

        dogMapper.insertDog(dogDto);
    }


}
