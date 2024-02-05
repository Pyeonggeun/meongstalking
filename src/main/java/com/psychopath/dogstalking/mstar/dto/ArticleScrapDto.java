package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArticleScrapDto {
    private int article_scrap_pk;
    private int article_pk;
    private int profile_info_pk;
    private LocalDateTime created_at;
}
