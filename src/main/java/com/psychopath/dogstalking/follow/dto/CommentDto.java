package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class CommentDto {
    private int comment_pk;
    private int user_pk;
    private int log_pk;
    private String content;
    private LocalDateTime created_date;
}
