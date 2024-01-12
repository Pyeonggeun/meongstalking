package com.psychopath.dogstalking.mstar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psychopath.dogstalking.dto.RestResponseDto;
import com.psychopath.dogstalking.mstar.dto.ProfileInfoDto;

@RestController
@RequestMapping("/mstar/*")
public class RestMstarController {

    public RestResponseDto insertProfileInfo(ProfileInfoDto profileInfoDto){
        RestResponseDto responseDto = new RestResponseDto();

        return responseDto;
    }
}
