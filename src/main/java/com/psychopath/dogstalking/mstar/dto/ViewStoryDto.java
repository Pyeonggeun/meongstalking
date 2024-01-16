package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ViewStoryDto {
    private int view_stroy_pk;
    private int story_pk;
    private int profile_info_pk;
    private LocalDateTime created_at;
}
