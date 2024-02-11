package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AchievementDto {
    private int achievement_pk;
    private int ach_catogory_pk;
    private String achievement_name;
    private String achievement_aim;
    private LocalDateTime created_at;
}
