package com.psychopath.dogstalking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
    private int user_pk;
    private String userid;
    private String userpw;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private String image;
    private LocalDateTime created_at;
}
