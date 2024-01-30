package com.psychopath.dogstalking.dto;

import lombok.Data;

@Data
public class KakaoUserDto {  
    private String code;
    private String kakaoUserId;
    private String nickname;
    private String profileImage;
}