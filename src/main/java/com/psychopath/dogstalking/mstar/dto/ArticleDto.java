package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArticleDto {
    private int article_pk;
    private int profile_info_pk;
    private String content;
    private String emotion;
    private LocalDateTime created_at;
}
