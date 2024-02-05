package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class UserItemDto {
    private int use_item_pk;
    private int user_pk;
    private int item_pk;
    private LocalDateTime created_at;
}
