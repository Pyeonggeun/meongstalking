package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class LikeDto {
    private int like_pk;
    private int user_collector_pk;
    private int user_writer_pk;
    private LocalDateTime created_date;
}
