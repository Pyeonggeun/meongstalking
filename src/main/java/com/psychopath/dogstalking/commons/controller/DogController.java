package com.psychopath.dogstalking.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psychopath.dogstalking.commons.service.DogServiceImpl;
import com.psychopath.dogstalking.dto.DogDto;
import com.psychopath.dogstalking.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commons/dog/*")
public class DogController {

    @Autowired
    private DogServiceImpl dogService;

    @RequestMapping("registerDogPage")
    public String registerDogPage(){

        return"/commons/dog/registerDogPage";
    }

    @RequestMapping("registerDogProcess")
    public String registerDogProcess(DogDto dogDto, HttpSession session){

        UserDto userDto = (UserDto)session.getAttribute("sessionUser");
        dogDto.setUserpk(userDto.getUser_pk());
        dogService.registerDog(dogDto);

        return"redirect:./registerDogPage";
    }


}
