package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ItemShopDto {
    private int item_pk;
    private String name;
    private int price;
    private String item_explain;
    private String item_category;
    private String item_img;
    private LocalDateTime created_at;
}
