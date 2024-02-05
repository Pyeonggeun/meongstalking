package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class OrderCoinDto {
    private int order_coin_pk;
    private int user_pk;
    private int coin_category_pk;
    private LocalDateTime created_at;
}
