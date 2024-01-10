package com.psychopath.dogstalking.funding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FundingWishlistDto {
    private int wishlist_pk;
    private int user_pk;
    private int product_pk;
    private LocalDateTime created_at;
}
