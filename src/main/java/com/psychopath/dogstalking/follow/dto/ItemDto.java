package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class ItemDto {
    private int item_pk;
    private String name;
    private int price;
    private LocalDateTime created_date;
}
