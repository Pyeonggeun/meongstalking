package com.psychopath.dogstalking.funding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FundingNewsDto {
    private int news_pk;
    private int product_pk;
    private String content;
    private LocalDateTime created_at;

}
