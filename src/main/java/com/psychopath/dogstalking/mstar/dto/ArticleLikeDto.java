package com.psychopath.dogstalking.mstar.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleLikeDto {
    private int article_like_pk;
    private int article_pk;
    private int profile_info_pk;
    private LocalDateTime created_at;
}
