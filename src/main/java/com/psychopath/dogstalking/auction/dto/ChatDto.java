package com.psychopath.dogstalking.auction.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatDto {
    private int pk;
    private int goods_pk;
    private int user_pk;
    private String message;
    private LocalDateTime created_at;
}
