package com.psychopath.dogstalking.mstar.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BookmarkDto {
    private int bookmark_pk;
    private int bkm_user_pk;
    private int rcv_user_pk;
    private LocalDateTime created_at;
}
