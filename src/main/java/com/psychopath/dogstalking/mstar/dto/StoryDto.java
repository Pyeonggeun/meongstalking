package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StoryDto {
    private int story_pk;
    private int profile_info_pk;
    private String story_photo;
    private String story_text;
    private double x_coordinates;
    private double y_coordinates;
    private String text_color;
    private LocalDateTime created_at;
}
