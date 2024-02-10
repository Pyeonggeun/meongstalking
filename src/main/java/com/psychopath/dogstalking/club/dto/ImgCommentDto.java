package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ImgCommentDto {
    private int imgcomment_pk;
    private int club_user_pk;
    private int clubimgboard_pk;
    private String content;
    private LocalDateTime created_at;
}
