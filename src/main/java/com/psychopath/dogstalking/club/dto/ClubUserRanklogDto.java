package com.psychopath.dogstalking.club.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubUserRanklogDto {
    private int clubuserranklog_pk;
    private int club_user_pk;
    private int clubuserranklogcategory_pk;
    private LocalDateTime created_at;
}
