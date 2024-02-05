package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubFreeBoardDto {
    private int clubfreeboard_pk;
    private int club_pk;
    private int user_pk;
    private String title;
    private String content;
    private String img;
    private int views;
    private LocalDateTime created_at;
}
