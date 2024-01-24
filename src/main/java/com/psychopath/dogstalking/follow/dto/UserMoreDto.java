package com.psychopath.dogstalking.follow.dto;

import lombok.Data;

@Data
public class UserMoreDto {
    private int user_more_pk;
    private int user_pk;
    private int height;
    private int weight;
    private String blood_type;
    private String mbti_type;
    private String hobby;
    private String drinking;
    private String smoking;
    private String preference;
}
