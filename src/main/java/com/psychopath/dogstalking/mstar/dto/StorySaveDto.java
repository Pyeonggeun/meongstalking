package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StorySaveDto {
    private int save_story_pk;
    private int story_pk;
    private int storage_pk;
    private LocalDateTime created_at;
}
