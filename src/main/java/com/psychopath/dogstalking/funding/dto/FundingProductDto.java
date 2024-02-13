package com.psychopath.dogstalking.funding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FundingProductDto {
    private int product_pk;
    private int user_pk;
    private String product_component;
    private String product_name;
    private String simple_explain;
    private String title_image;
    private String explain_image;
    private LocalDateTime start_date;
    private LocalDateTime finish_date;
    private int price;
    private int amount;
    private LocalDateTime created_at;

}
