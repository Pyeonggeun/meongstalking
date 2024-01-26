package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class TcommentDto {
    private int t_comment_pk;
    private int comment_pk;
    private int profile_info_pk;
    private String t_comment;
    private LocalDateTime created_at;
}
