package com.psychopath.dogstalking.trade.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CarePriceDto {
    private int pk;
    private int article_pk;
    private int price;
    private LocalDateTime created_at;
}
