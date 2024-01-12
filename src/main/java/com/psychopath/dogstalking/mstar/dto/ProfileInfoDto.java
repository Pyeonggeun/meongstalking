package com.psychopath.dogstalking.mstar.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProfileInfoDto {
    private int profile_info_pk;
    private int user_pk;
    private String profile_photo;
    private String nick_name;
    private String status_message;
    private LocalDateTime created_at;
}
