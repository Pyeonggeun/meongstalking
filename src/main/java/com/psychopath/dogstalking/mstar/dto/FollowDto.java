package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FollowDto {
    private int follow_pk;
    private int follow_user_pk;
    private int following_user_pk;
    private LocalDateTime created_at;
}
