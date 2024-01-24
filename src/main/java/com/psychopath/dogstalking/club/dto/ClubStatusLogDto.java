package com.psychopath.dogstalking.club.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClubStatusLogDto {
    private int clubstatuslog_pk;
    private int club_user_pk;
    private int clubcategory_pk;
    private LocalDateTime created_at;
}
