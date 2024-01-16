package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubUserDto {
    private int club_user_pk;
    private int user_pk;
    private int club_pk;
    private String content;
    private LocalDateTime created_at;
}
