package com.psychopath.dogstalking.mstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.mstar.service.MstarServiceImpl;

@RestController
@RequestMapping("/mstar/*")
public class RestMstarController {
    @Autowired
    private MstarServiceImpl msterService;

    @RequestMapping("loadMyProfileInfo")
    public RestResponseDto getMyProfileInfo(int profile_info_pk){
        RestResponseDto responseDto = new RestResponseDto();

        responseDto.setResult("success");
        responseDto.setData(null);

        return responseDto;

    }
}
