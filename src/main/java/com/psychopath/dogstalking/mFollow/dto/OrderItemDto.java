package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class OrderItemDto {
    private int order_item_pk;
    private int user_pk;
    private int item_pk;
    private int quantity;
    private LocalDateTime created_at;
}
