package com.psychopath.dogstalking.auction.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BidDto {
    private int pk;
    private int goods_pk;
    private int user_pk;
    private int bid_price;
    private LocalDateTime created_at;
}
