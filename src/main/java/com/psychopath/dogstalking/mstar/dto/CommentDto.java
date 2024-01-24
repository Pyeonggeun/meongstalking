package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDto {
    private int comment_pk;
    private int article_pk;
    private int profile_info_pk;
    private String comment;
    private LocalDateTime created_at;
}
