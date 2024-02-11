package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AchievementCategoryDto {
    private int ach_catogory_pk;
    private String name;
    private LocalDateTime created_at;
}
