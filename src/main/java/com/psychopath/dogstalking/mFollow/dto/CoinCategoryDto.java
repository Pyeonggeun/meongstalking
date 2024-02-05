package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CoinCategoryDto {
    private int coin_category_pk;
    private int coin_count;
    private int bonus_coin;
    private int price;
    private String coin_img;
    private LocalDateTime created_at;
}
