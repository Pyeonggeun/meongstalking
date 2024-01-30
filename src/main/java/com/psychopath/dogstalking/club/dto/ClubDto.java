package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubDto {
    private int club_pk;
    private String name;
    private String content;
    private String img;
    private LocalDateTime created_at;
}
