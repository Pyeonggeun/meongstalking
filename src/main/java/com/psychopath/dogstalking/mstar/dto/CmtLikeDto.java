package com.psychopath.dogstalking.mstar.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CmtLikeDto {
    private int cmt_like_pk;
    private int comment_pk;
    private int profile_info_pk;
    private LocalDateTime created_at;
}
