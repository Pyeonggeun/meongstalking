package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubImgBoardDto {
    private int clubimgboard_pk;
    private int club_user_pk;
    private int club_pk;
    private String img;
    private String imgs;
    private int views;
    private LocalDateTime created_at;
}
