package com.psychopath.dogstalking.mFollow.dto;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AchievementLevelDto {
    private int achieve_lv_pk;
    private int achievement_pk;
    private int ach_count;
    private String ach_level;
    private LocalDateTime created_at;
}
