package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class LogDto {
    private int log_pk;
    private int user_more_pk;
    private String image_link;
    private String content;
    private double latitude;
    private double longitude;
    private LocalDateTime created_date;
}
