package com.psychopath.dogstalking.funding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FundingOrderDto {
    private int order_pk;
    private int user_pk;
    private int product_pk;
    private int quantity;
    private LocalDateTime order_date;
    private LocalDateTime created_at;
}
