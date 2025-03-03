package com.psychopath.dogstalking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DogDto {
    
    private int pk;
    private int userpk;
    private String name;
    private LocalDate birth;
    private String weight;
    private String gender;
    private String image;
    private String isNeutering;
    private String breed;
    private LocalDateTime created_at;


}
