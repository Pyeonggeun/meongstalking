package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class AddItemDto {
    private int add_item_pk;
    private int user_pk;
    private int item_pk;
    private int quantity;
    private LocalDateTime created_date;
}
