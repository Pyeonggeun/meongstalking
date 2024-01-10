package com.psychopath.dogstalking.follow.dto;

import lombok.Data;
import java.time.*;

@Data
public class UseItemDto {
    private int use_item_pk;
    private int user_pk;
    private int item_pk;
    private LocalDateTime created_date;
}
