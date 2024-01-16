package com.psychopath.dogstalking.club.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDto {
    private int comment_pk;
    private int user_pk;
    private int clubfreeboard_pk;
    private String content;
    private LocalDateTime created_at;
}
