package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubArticleImgDto {
    private int imgboardimgs_pk;
    private int clubimgboard_pk;
    private String location;
    private String Original_filename;
    private LocalDateTime created_at;
}
