package com.psychopath.dogstalking.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.psychopath.dogstalking.commons.mapper.DogSqlMapper;
import com.psychopath.dogstalking.dto.DogDto;

import jakarta.servlet.http.HttpSession;

@Service
public class DogServiceImpl {

    @Autowired
    private DogSqlMapper dogMapper;

    public void registerDog(DogDto dogDto){

        dogMapper.insertDog(dogDto);
    }

    public List<DogDto> getDogList(int userPk){


        return dogMapper.getDogList(userPk);
    }


    public DogDto getDogInfo(int dogPk){

        return dogMapper.getDogInfo(dogPk);
    }

}
