package com.psychopath.dogstalking.follow.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LikeLogDto {
    private int like_log_pk;
    private int log_pk;
    private double latitude;
    private double longitude;
    private LocalDateTime created_date;
}
