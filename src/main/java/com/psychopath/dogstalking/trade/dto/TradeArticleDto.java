package com.psychopath.dogstalking.trade.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
public class TradeArticleDto {
    private int pk;
    private int user_pk;
    private String title;
    private String content;
    private LocalDate start_care_date;
    private LocalDate end_care_date;
    private int read_count;
    private String location;
    private String isPickup;
    private String isDelete;
    private LocalDateTime created_at;

}
