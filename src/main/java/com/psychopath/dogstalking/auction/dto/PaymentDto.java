package com.psychopath.dogstalking.auction.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentDto {
    private int pk;
    private int bid_pk;
    private int price;
    private String address;
    private LocalDateTime created_at;
}
