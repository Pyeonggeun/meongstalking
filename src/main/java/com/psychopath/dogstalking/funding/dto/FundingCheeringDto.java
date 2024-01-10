package com.psychopath.dogstalking.funding.dto;

import lombok.Data;

@Data
public class FundingCheeringDto {
    private int cheering_pk;
    private int user_pk;
    private int product_pk;
    private String text;
}
