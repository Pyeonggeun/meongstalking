package com.psychopath.dogstalking.funding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FundingReviewDto {
    private int review_pk;
    private int order_pk;
    private int user_pk;
    private int product_pk;
    private String content;
    private String review_image;
    private LocalDateTime created_at;
}
