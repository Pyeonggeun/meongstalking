package com.psychopath.dogstalking.auction.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeliveryDto {
    private int pk;
    private int payment_pk;
    private String waybill;
    private LocalDateTime created_at;
}
